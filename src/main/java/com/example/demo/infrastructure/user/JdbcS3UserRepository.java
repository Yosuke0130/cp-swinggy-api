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
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

            Date createdDate = new Date();
            jdbc.update("insert into user(user_id, created_at) values(?, ?)", user.getUserId(), createdDate);

            jdbc.update(
                    "insert into user_profile(user_profile_id, user_id, first_name, last_name, screen_name, profile_image_path, email, tel)" +
                            " values(?, ?, ?, ?, ?, ?, ?, ?)",
                    user.getUserProfileId().getValue(),
                    user.getUserId(),
                    user.getFirstName().getValue(),
                    user.getLastName().getValue(),
                    user.getScreenName().getValue(),
                    user.getProfileImageURL().getValue().toString(),
                    user.getEmail().getValue(),
                    user.getTel().getValue());

            logger.debug("アップロードパス" + user.getProfileImageURL());

        } catch (DataAccessException e) {
            throw new UserCreateException("DB access error when insert user data", e);

        } catch (IOException e) {
            //s3アップロードエラー
            throw new IllegalStateException("upload file is not accepted", e);
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
    public List<Map<String, Object>> selectByTel(User user) {

        List<Map<String, Object>> userSelectedByTel = jdbc.queryForList("select * from user_profile where tel = ?", user.getTel().getValue());

        return userSelectedByTel;
    }


    @Override
    @Transactional
    public List<Map<String, Object>> selectByEmail(User user) {

        List<Map<String, Object>> userSelectedByEmail = jdbc.queryForList("select * from user_profile where email = ?", user.getEmail().getValue());

        return userSelectedByEmail;
    }


    @Override
    @Transactional
    public List<Map<String, Object>> selectByScreenName(User user) {

        List<Map<String, Object>> userSelectedByScreenName = jdbc.queryForList("select * from user_profile where screen_name = ?", user.getScreenName().getValue());

        return userSelectedByScreenName;
    }


    @Override
    @Transactional
    public User find(String userId) throws UserCreateException, IllegalStateException {
        try {

            Map<String, Object> userData = jdbc.queryForMap("select * from user_profile where user_id = ?", userId);

            if (userData.size() < 1) {
                throw new IllegalStateException("User Data doesn't exist");
            }

            URL url = new URL((String) userData.get("profile_image_path"));

            User user = new User((String) userData.get("user_id"),
                    (String) userData.get("user_profile_id"),
                    (String) userData.get("first_name"),
                    (String) userData.get("last_name"),
                    (String) userData.get("screen_name"),
                    (String) userData.get("email"),
                    (String) userData.get("tel"),
                    url);

            return user;

        } catch (MalformedURLException e) {
            throw new UserCreateException("Image path URL couldn't be issued.", e);

        } catch (DataAccessException e) {
            throw new UserCreateException("Data access error occurred when getting user_profile data.", e);

        }
    }
}
