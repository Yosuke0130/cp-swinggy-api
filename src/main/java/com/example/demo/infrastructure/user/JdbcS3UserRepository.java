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
    public void insert(User user, Optional<MultipartFile> profileImage) throws UserCreateException {
        try {

            if (profileImage.isPresent()) {
                //拡張子取得、バイトに変換、アップロードメソッド呼び出し、パスをuserにセット
                String contentType = profileImage.get().getContentType();
                byte[] uploadImage = profileImage.get().getBytes();
                URL url = uploadImage(uploadImage, user, contentType);
                user.setProfileImageURL(url);
                System.out.println(user.getProfileImageURL());
            }

            Date createdDate = new Date();
            jdbc.update("insert into user(user_id, created_at) values(?, ?)", user.getUserId(), createdDate);

            jdbc.update(
                    "insert into user_profile(user_profile_id, user_id, first_name, last_name, screen_name, profile_image_path, email, tel)" +
                            " values(?, ?, ?, ?, ?, ?, ?, ?)",
                    user.getUserProfileId(), user.getUserId(), user.getFirstName(), user.getLastName(), user.getScreenName(), user.getProfileImageURL().toString(), user.getEmail(), user.getTel());

            logger.debug("アップロードパス" + user.getProfileImageURL());

        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new UserCreateException("DB access error");

        } catch (IOException e) {
            //s3アップロードエラー
            throw new IllegalArgumentException("upload file is not accepted");
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
    public URL uploadImage(byte[] uploadImage, User user, String contentType) throws IOException {

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
            String imageType = getFileExtension(contentType);
            // リクエストを生成
            PutObjectRequest request = new PutObjectRequest(
                    bucketName,
                    profileImageDirectory + "/" + user.getUserProfileId() + "." + imageType,
                    input,
                    metaData);
            // アップロード
            s3Client.putObject(request);
        }
        return s3Client.getUrl(bucketName, profileImageDirectory);
    }

    //"image/jpeg"を"jpeg"に変換して拡張子として使えるようにするメソッド
    public String getFileExtension(String contentType) throws IllegalArgumentException {
        if (contentType.equals("image/jpeg")) {
            return "jpeg";
        } else if (contentType.equals("image/png")) {
            return "png";
        } else {
            throw new IllegalArgumentException("Data Type should be jpeg or png");
        }
    }

    @Override
    @Transactional
    public List<Map<String, Object>> selectByTel(User user) {

        List<Map<String, Object>> userSelectedByTel = jdbc.queryForList("select * from user_profile where tel = ?",user.getTel());

        return userSelectedByTel;
    }

    @Override
    @Transactional
    public List<Map<String, Object>> selectByEmail(User user) {

        List<Map<String, Object>> userSelectedByEmail = jdbc.queryForList("select * from user_profile where email = ?", user.getEmail());

        return userSelectedByEmail;
    }

    @Override
    @Transactional
    public List<Map<String, Object>> selectByScreenName(User user) {

        List<Map<String, Object>> userSelectedByScreenName = jdbc.queryForList("select * from user_profile where screen_name = ?", user.getScreenName());

        return userSelectedByScreenName;
    }
}
