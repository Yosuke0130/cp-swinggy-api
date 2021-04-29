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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

@Repository
public class JdbcS3UserRepository implements UserRepository {

    @Autowired
    Logging logger;

    @Autowired
    JdbcTemplate jdbc;

    @Override
    @Transactional
    public void insert(User user, byte[] profileImage) throws UserCreateException {
        try {

            uploadImage(profileImage, user);

            Date createdDate = new Date();
            String profileImagePath = getProfileImagePath(user);

            jdbc.update("insert into user(user_id, created_at) values(?, ?)", user.getUserId(), createdDate);

            jdbc.update(
            "insert into user_profile(user_profile_id, user_id, first_name, last_name, screen_name, profile_image_path, email, tel)" +
                    " values(?, ?, ?, ?, ?, ?, ?, ?)",
            user.getUserProfileId(), user.getUserId(), user.getFirstName(), user.getLastName(), user.getScreenName(), profileImagePath, user.getEmail(), user.getTel());

            logger.debug("insert success!");

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

    //S3ファイルアップロード
    public void uploadImage(byte[] profileImage, User user) throws IOException {

        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

        AmazonS3 s3Client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(awsRegion)
                .build();

        try(InputStream input = new ByteArrayInputStream(profileImage)) {
            // メタ情報を生成
            ObjectMetadata metaData = new ObjectMetadata();
            metaData.setContentLength(profileImage.length);
            // リクエストを生成
            PutObjectRequest request = new PutObjectRequest(
                    bucketName, "profile_image" + "/" + user.getScreenName() + ".jpg", input, metaData);
            // アップロード
            s3Client.putObject(request);
        }
    }

    public String getProfileImagePath(User user) {
        return "https://" + bucketName + ".s3-" + awsRegion + ".amazonaws.com/profile_image/" + user.getScreenName() + ".jpg";
    }

    @Override
    @Transactional
    public int selectTel(User user) {

        Integer telData = jdbc.queryForObject("select count(tel) from user_profile where tel = ?", Integer.class, user.getTel());

        return telData;
    }

    @Override
    @Transactional
    public int selectEmail(User user) {

        Integer emailData = jdbc.queryForObject("select count(email) from user_profile where email = ?", Integer.class, user.getEmail());

        return emailData;
    }

    @Override
    @Transactional
    public int selectScreenName(User user) {

        Integer screenNameData = jdbc.queryForObject("select count(screen_name) from user_profile where screen_name = ?", Integer.class, user.getScreenName());

        return screenNameData;
    }

}
