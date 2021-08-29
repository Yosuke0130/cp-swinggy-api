package com.example.demo.infrastructure.user;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.demo.Logging;
import com.example.demo.application.user.UserCreateException;
import com.example.demo.domain.user.User;
import com.example.demo.domain.user.UserRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

@Repository
public class JdbcS3UserRepository implements UserRepository {

    @Autowired
    Logging logger;

    @Autowired
    JdbcTemplate jdbc;


    @Override
    @Transactional
    public void insert(User user, Optional<MultipartFile> profileImage) throws UserCreateException, IllegalStateException {
        try {
            if (profileImage.isPresent()) {
                //拡張子取得、バイトに変換、アップロードメソッド呼び出し、パスをuserにセット
                String contentType = profileImage.get().getContentType();
                byte[] uploadImage = profileImage.get().getBytes();
                String extension = FilenameUtils.getExtension(profileImage.get().getOriginalFilename());
                URL profileImageUrl = uploadImage(uploadImage, user, contentType, extension);
                user.setProfileImageURL(profileImageUrl);
            }

            jdbc.update("INSERT INTO user(user_id) VALUES(?)", user.getUserId());
            if (user.getTel().isPresent()) {
                jdbc.update(
                        "INSERT INTO user_profile(user_profile_id, user_id, first_name, last_name, screen_name, profile_image_path, email, tel)" +
                                " VALUES(?, ?, ?, ?, ?, ?, ?, ?)",
                        user.getUserProfileId().getValue(),
                        user.getUserId(),
                        user.getFirstName().getValue(),
                        user.getLastName().getValue(),
                        user.getScreenName().getValue(),
                        user.getProfileImageURL().getValue().toString(),
                        user.getEmail().getValue(),
                        user.getTel().get().getValue());
            } else {
                jdbc.update(
                        "INSERT INTO user_profile(user_profile_id, user_id, first_name, last_name, screen_name, profile_image_path, email)" +
                                " VALUES(?, ?, ?, ?, ?, ?, ?)",
                        user.getUserProfileId().getValue(),
                        user.getUserId(),
                        user.getFirstName().getValue(),
                        user.getLastName().getValue(),
                        user.getScreenName().getValue(),
                        user.getProfileImageURL().getValue().toString(),
                        user.getEmail().getValue());
                logger.debug("User created with not tel no.");
            }
            logger.debug("アップロードパス" + user.getProfileImageURL());

        } catch (DataAccessException e) {
            throw new UserCreateException("DB access error when insert user data.", e);

        } catch (IOException e) {
            //s3アップロードエラー
            throw new UserCreateException("uploading profile image failed.", e);
        }
    }


    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Value("${cloud.aws.region.static}")
    private String awsRegion;

    @Value("${s3.bucketName}")
    private String bucketName;

    @Value("${profile.image.directory}")
    private String profileImageDirectory;


    //S3ファイルアップロード パスを返却
    private URL uploadImage(byte[] uploadImage, User user, String contentType, String fileExtension) throws IOException {

        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

        AmazonS3 s3Client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(awsRegion)
                .build();

        try (InputStream input = new ByteArrayInputStream(uploadImage)) {
            // メタ情報を生成
            ObjectMetadata metaData = new ObjectMetadata();
            metaData.setContentLength(uploadImage.length);
            metaData.setContentType(contentType);

            // リクエストを生成
            PutObjectRequest request = new PutObjectRequest(
                    bucketName,
                    profileImageDirectory + "/" + user.getUserProfileId() + "." + fileExtension,
                    input,
                    metaData);
            // アップロード
            s3Client.putObject(request);
        }
        return s3Client.getUrl(bucketName, profileImageDirectory);
    }


    @Override
    @Transactional
    public List<User> selectByTel(User user) {

        List<Map<String, Object>> userDateSelectedByTel = jdbc.queryForList("SELECT * FROM user_profile WHERE tel = ?", user.getTel().get().getValue());
        try {
            List<User> userList = new ArrayList<>();
            for (Map<String, Object> value : userDateSelectedByTel) {
                User userSelectedByTel = convertToUser(value);
                userList.add(userSelectedByTel);
            }
            return userList;

        } catch (MalformedURLException e) {
            e.getMessage();
            throw new UserCreateException("Image URL path couldn't be issued.", e);
        }

    }


    @Override
    @Transactional
    public List<User> selectByEmail(User user) {

        List<Map<String, Object>> userDataSelectedByEmail = jdbc.queryForList("SELECT * FROM user_profile WHERE email = ?", user.getEmail().getValue());
        try {
            List<User> userList = new ArrayList<>();
            for (Map<String, Object> value : userDataSelectedByEmail) {
                User userSelectedByEmail = convertToUser(value);
                userList.add(userSelectedByEmail);
            }
            return userList;

        } catch (MalformedURLException e) {
            e.getMessage();
            throw new UserCreateException("Image URL path couldn't be issued.", e);
        }

    }


    @Override
    @Transactional
    public List<User> selectByScreenName(User user) {

        List<Map<String, Object>> userDataSelectedByScreenName = jdbc.queryForList("SELECT * FROM user_profile WHERE screen_name = ?", user.getScreenName().getValue());
        try {
            List<User> userList = new ArrayList<>();
            for (Map<String, Object> value : userDataSelectedByScreenName) {
                User userSelectedByScreenName = convertToUser(value);
                userList.add(userSelectedByScreenName);
            }
            return userList;

        } catch (MalformedURLException e) {
            e.getMessage();
            throw new UserCreateException("Image URL path couldn't be issued.", e);
        }

    }


    @Override
    @Transactional
    public User select(String userId) throws UserCreateException, IllegalStateException {
        try {

            Map<String, Object> userData = jdbc.queryForMap("SELECT * FROM user_profile WHERE user_id = ?", userId);

            URL url = new URL((String) userData.get("profile_image_path"));

            User user = new User((String) userData.get("user_id"),
                    (String) userData.get("user_profile_id"),
                    (String) userData.get("first_name"),
                    (String) userData.get("last_name"),
                    (String) userData.get("screen_name"),
                    (String) userData.get("email"),
                    Optional.ofNullable((String) userData.get("tel")),
                    url);

            return user;

        } catch (MalformedURLException e) {
            throw new UserCreateException("Image URL path couldn't be issued.", e);

        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new IllegalStateException("User Data doesn't exist", e);

        }
    }

    @Override
    @Transactional
    public List<User> selectUsers(int page, int per) throws UserCreateException {

        int offset = 0;
        if (page > 0) {
            offset = page * per;
        }

        List<Map<String, Object>> userData = jdbc.queryForList("SELECT * FROM user_profile ORDER BY user_id LIMIT ? OFFSET ?", per, offset);
        try {
            List<User> userList = new ArrayList<>();
            for (Map<String, Object> value : userData) {
                User user = convertToUser(value);
                userList.add(user);
            }
            return userList;

        } catch (MalformedURLException e) {
            e.getMessage();
            throw new UserCreateException("Image URL path couldn't be issued.", e);
        }

    }

    private User convertToUser(Map<String, Object> userData) throws MalformedURLException {
        URL url = new URL((String) userData.get("profile_image_path"));

        return new User((String) userData.get("user_id"),
                (String) userData.get("user_profile_id"),
                (String) userData.get("first_name"),
                (String) userData.get("last_name"),
                (String) userData.get("screen_name"),
                (String) userData.get("email"),
                Optional.ofNullable((String) userData.get("tel")),
                url);
    }

    @Override
    @Transactional
    public int selectCount() {

        Integer count = jdbc.queryForObject("SELECT COUNT(*) FROM user_profile", Integer.class);

        return count;
    }

    @Override
    @Transactional
    public int selectCountByScreenName(String screenName) throws UserCreateException {
        try {
            Integer count = jdbc.queryForObject("SELECT COUNT(*) FROM user_profile WHERE screen_name = ?", Integer.class, screenName);

            return count;
        } catch (DataAccessException e) {
            e.getMessage();
            throw new UserCreateException("Couldn't get ScreenName count from DB.");
        }
    }

    @Override
    public boolean exists(String userId) {
        try {
            Integer count = jdbc.queryForObject("SELECT COUNT(*) FROM user WHERE user_id = ?", Integer.class,userId);

            return count > 0;

        } catch (DataAccessException e) {
            return false;
        }
    }
}
