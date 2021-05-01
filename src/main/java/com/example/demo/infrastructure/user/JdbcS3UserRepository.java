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

            if(!profileImage.isPresent()) {
                //nullの場合、デフォルト画像セット
                String defaultImagePath = "https://bucket-for-golfapp.s3-ap-northeast-1.amazonaws.com/profile_image/default_Image.jpeg";
                user.setProfileImagePath(defaultImagePath);
            } else {
                //拡張子取得、バイトに変換、アップロードメソッド呼び出し、パスをuserにセット
                String contentType = profileImage.get().getContentType();
                byte[] uploadImage = profileImage.get().getBytes();
                URL url = uploadImage(uploadImage, user, contentType);
                user.setProfileImagePath(url.toString());
            }

            Date createdDate = new Date();
            jdbc.update("insert into user(user_id, created_at) values(?, ?)", user.getUserId(), createdDate);

            jdbc.update(
            "insert into user_profile(user_profile_id, user_id, first_name, last_name, screen_name, profile_image_path, email, tel)" +
                    " values(?, ?, ?, ?, ?, ?, ?, ?)",
            user.getUserProfileId(), user.getUserId(), user.getFirstName(), user.getLastName(), user.getScreenName(), user.getProfileImagePath(), user.getEmail(), user.getTel());

            logger.debug("アップロードパス" + user.getProfileImagePath());

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

    //S3ファイルアップロード パスを返却
    public URL uploadImage(byte[] uploadImage, User user, String contentType) throws IOException {

        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

        AmazonS3 s3Client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(awsRegion)
                .build();
        String objectDirectory = "profile_image";

        try(InputStream input = new ByteArrayInputStream(uploadImage)) {
            // メタ情報を生成
            ObjectMetadata metaData = new ObjectMetadata();
            metaData.setContentLength(uploadImage.length);
            metaData.setContentType(contentType);
            String imageType = checkContentType(contentType);
            // リクエストを生成
            PutObjectRequest request = new PutObjectRequest(
                bucketName,
                objectDirectory + "/" + user.getUserProfileId() + "." + imageType,
                input,
                metaData);
            // アップロード
            s3Client.putObject(request);
        }
        return s3Client.getUrl(bucketName,objectDirectory);
    }

    //"imge/jpeg"を"jpeg"に変換して拡張子として使えるようにするメソッド
    public String checkContentType(String contentType) throws IllegalArgumentException{
        if(contentType.equals("image/jpeg")) {
            return "jpeg";
        } else if(contentType.equals("image/png")) {
            return "png";
        } else {
            throw new IllegalArgumentException("Data Type should be jpeg or png");
        }
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
