package com.example.demo.user;

import com.example.demo.domain.user.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class TestUser {

    private final static String TEST_USER_ID = "1234";
    private final static String TEST_FIRSTNAME = "sample";
    private final static String TEST_LASTNAME = "taro";
    private final static String TEST_SCREEN_NAME = "SampleTaro";
    private final static String TEST_EMAIL = "sample@test.com";
    private final static Optional<String> TEST_TEL = Optional.of("0123456789");

    @Test
    public void createUserInstance() {

        try {
            URL testDefaultURL = new URL("https://bucket-for-golfapp.s3-ap-northeast-1.amazonaws.com/profile_image/default_Image.jpeg");

            User testUser = new User(TEST_USER_ID, TEST_FIRSTNAME, TEST_LASTNAME, TEST_SCREEN_NAME, TEST_EMAIL, TEST_TEL, testDefaultURL);

            String resultOfGetUserId = testUser.getUserId();
            String resultOfGetFirstName = testUser.getFirstName().getValue();
            String resultOfGetScreenName = testUser.getScreenName().getValue();
            String resultOfGetEmail = testUser.getEmail().getValue();
            Optional<String> resultOfGetTel = Optional.of(testUser.getTel().get().getValue());
            String resultOfGetProfileImageURL = testUser.getProfileImageURL().getValue().toString();

            assertAll(
                    () -> assertEquals("1234", resultOfGetUserId),
                    () -> assertEquals("sample", resultOfGetFirstName),
                    () -> assertEquals("SampleTaro", resultOfGetScreenName),
                    () -> assertEquals("sample@test.com", resultOfGetEmail),
                    () -> assertEquals(Optional.of("0123456789"), resultOfGetTel),
                    () -> assertEquals("https://bucket-for-golfapp.s3-ap-northeast-1.amazonaws.com/profile_image/default_Image.jpeg", resultOfGetProfileImageURL)
            );
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
