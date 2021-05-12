package com.example.demo.user;

import com.example.demo.application.user.UserCreateException;
import com.example.demo.domain.user.User;
import com.example.demo.domain.user.UserRepository;
import com.example.demo.domain.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class TestUserService {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private final static String TEST_USER_ID = "1234";
    private final static String TEST_FIRSTNAME = "sample";
    private final static String TEST_LASTNAME = "taro";
    private final static String TEST_SCREEN_NAME = "SampleTaro";
    private final static String TEST_EMAIL = "sample@test.com";
    private final static String TEST_TEL = "0123456789";
    private final static Optional<MultipartFile> TEST_PROFILE_IMAGE = Optional.empty();

    @Test
    public void telExistsIsTrue() {
        try {
            URL testDefaultURL = new URL("https://bucket-for-golfapp.s3-ap-northeast-1.amazonaws.com/profile_image/default_Image.jpeg");

            User testUser = new User(TEST_USER_ID, TEST_FIRSTNAME, TEST_LASTNAME, TEST_SCREEN_NAME, TEST_EMAIL, TEST_TEL, testDefaultURL);

            Map<String, Object> map = new HashMap<>();
            map.put("user_id", TEST_USER_ID);
            map.put("first_name", TEST_FIRSTNAME);
            map.put("email", TEST_EMAIL);

            List<Map<String, Object>> users = new ArrayList<>();
            users.add(map);

            doReturn(users).when(userRepository).selectByTel(any());

            boolean result = userService.telExists(testUser);

            assertEquals(true, result);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void telExistsIsFalse() {
        try {
            URL testDefaultURL = new URL("https://bucket-for-golfapp.s3-ap-northeast-1.amazonaws.com/profile_image/default_Image.jpeg");

            User testUser = new User(TEST_USER_ID, TEST_FIRSTNAME, TEST_LASTNAME, TEST_SCREEN_NAME, TEST_EMAIL, TEST_TEL, testDefaultURL);

            List<Map<String, Object>> users = Collections.emptyList();

            System.out.println(users);

            doReturn(users).when(userRepository).selectByTel(any());

            boolean result = userService.telExists(testUser);

            assertEquals(false, result);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void emailExistsIsTrue() {
        try {
            URL testDefaultURL = new URL("https://bucket-for-golfapp.s3-ap-northeast-1.amazonaws.com/profile_image/default_Image.jpeg");

            User testUser = new User(TEST_USER_ID, TEST_FIRSTNAME, TEST_LASTNAME, TEST_SCREEN_NAME, TEST_EMAIL, TEST_TEL, testDefaultURL);

            Map<String, Object> map = new HashMap<>();
            map.put("user_id", TEST_USER_ID);
            map.put("first_name", TEST_FIRSTNAME);
            map.put("email", TEST_EMAIL);

            List<Map<String, Object>> users = new ArrayList<>();
            users.add(map);

            doReturn(users).when(userRepository).selectByEmail(any());

            boolean result = userService.emailExists(testUser);

            assertEquals(true, result);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void emailExistsIsFalse() {
        try {
            URL testDefaultURL = new URL("https://bucket-for-golfapp.s3-ap-northeast-1.amazonaws.com/profile_image/default_Image.jpeg");

            User testUser = new User(TEST_USER_ID, TEST_FIRSTNAME, TEST_LASTNAME, TEST_SCREEN_NAME, TEST_EMAIL, TEST_TEL, testDefaultURL);

            List<Map<String, Object>> users = Collections.emptyList();

            System.out.println(users);

            doReturn(users).when(userRepository).selectByEmail(any());

            boolean result = userService.emailExists(testUser);

            assertEquals(false, result);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void screenNameExistsIsTrue() {
        try {
            URL testDefaultURL = new URL("https://bucket-for-golfapp.s3-ap-northeast-1.amazonaws.com/profile_image/default_Image.jpeg");

            User testUser = new User(TEST_USER_ID, TEST_FIRSTNAME, TEST_LASTNAME, TEST_SCREEN_NAME, TEST_EMAIL, TEST_TEL, testDefaultURL);

            Map<String, Object> map = new HashMap<>();
            map.put("user_id", TEST_USER_ID);
            map.put("first_name", TEST_FIRSTNAME);
            map.put("email", TEST_EMAIL);

            List<Map<String, Object>> users = new ArrayList<>();
            users.add(map);

            doReturn(users).when(userRepository).selectByScreenName(any());

            boolean result = userService.screenNameExists(testUser);

            assertEquals(true, result);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void screenNameExistsIsFalse() {
        try {
            URL testDefaultURL = new URL("https://bucket-for-golfapp.s3-ap-northeast-1.amazonaws.com/profile_image/default_Image.jpeg");

            User testUser = new User(TEST_USER_ID, TEST_FIRSTNAME, TEST_LASTNAME, TEST_SCREEN_NAME, TEST_EMAIL, TEST_TEL, testDefaultURL);

            List<Map<String, Object>> users = Collections.emptyList();

            System.out.println(users);

            doReturn(users).when(userRepository).selectByScreenName(any());

            boolean result = userService.screenNameExists(testUser);

            assertEquals(false, result);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void usersIsNull() {
        try {
            URL testDefaultURL = new URL("https://bucket-for-golfapp.s3-ap-northeast-1.amazonaws.com/profile_image/default_Image.jpeg");

            User testUser = new User(TEST_USER_ID, TEST_FIRSTNAME, TEST_LASTNAME, TEST_SCREEN_NAME, TEST_EMAIL, TEST_TEL, testDefaultURL);

            List<Map<String, Object>> users = null;

            doReturn(users).when(userRepository).selectByTel(any());

            UserCreateException e = assertThrows(UserCreateException.class, () -> userService.telExists(testUser));

            assertEquals("Unexpected null value was returned from UserRepository.", e.getMessage());


        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
