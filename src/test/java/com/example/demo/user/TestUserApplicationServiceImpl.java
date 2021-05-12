package com.example.demo.user;

import com.example.demo.application.user.UserApplicationServiceImpl;
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

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class TestUserApplicationServiceImpl {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserApplicationServiceImpl userApplicationServiceImpl;

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
    private final static String TEST_URL = "https://bucket-for-golfapp.s3-ap-northeast-1.amazonaws.com/profile_image/default_Image.jpeg";

    @Test
    public void profileImageIsEmplty() {

        //正常な実行パターン
        doReturn(false).when(userService).telExists(any());
        doReturn(false).when(userService).emailExists(any());
        doReturn(false).when(userService).screenNameExists(any());
        doNothing().when(userRepository).insert(any(), any());

        //実行
        userApplicationServiceImpl.create(TEST_USER_ID, TEST_FIRSTNAME, TEST_LASTNAME, TEST_SCREEN_NAME, TEST_PROFILE_IMAGE, TEST_EMAIL, TEST_TEL);

        //期待する挙動と実際の結果を比較
        assertFalse(userService.telExists(any()));
        assertFalse(userService.emailExists(any()));
        assertFalse(userService.screenNameExists(any()));
    }


    @Test
    public void telIsDuplicated() {
        //TELの重複パターン
        doReturn(true).when(userService).telExists(any());
        doReturn(false).when(userService).emailExists(any());
        doReturn(false).when(userService).screenNameExists(any());
        doNothing().when(userRepository).insert(any(), any());

        //実行、例外スロー確認
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> userApplicationServiceImpl.create(TEST_USER_ID, TEST_FIRSTNAME, TEST_LASTNAME, TEST_SCREEN_NAME, TEST_PROFILE_IMAGE, TEST_EMAIL, TEST_TEL));

        assertEquals("This tel number has already existed", e.getMessage());
    }


    @Test
    public void emailIsDuplicated() {
        //Emailの重複パターン
        doReturn(false).when(userService).telExists(any());
        doReturn(true).when(userService).emailExists(any());
        doReturn(false).when(userService).screenNameExists(any());
        doNothing().when(userRepository).insert(any(), any());

        //実行、例外スロー確認
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> userApplicationServiceImpl.create(TEST_USER_ID, TEST_FIRSTNAME, TEST_LASTNAME, TEST_SCREEN_NAME, TEST_PROFILE_IMAGE, TEST_EMAIL, TEST_TEL));

        assertEquals("This email address has already existed", e.getMessage());
    }


    @Test
    public void screenNameIsDuplicated() throws IllegalArgumentException {
        //ScreenNameの重複パターン
        doReturn(false).when(userService).telExists(any());
        doReturn(false).when(userService).emailExists(any());
        doReturn(true).when(userService).screenNameExists(any());
        doNothing().when(userRepository).insert(any(), any());

        //実行、例外スロー確認
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> userApplicationServiceImpl.create(TEST_USER_ID, TEST_FIRSTNAME, TEST_LASTNAME, TEST_SCREEN_NAME, TEST_PROFILE_IMAGE, TEST_EMAIL, TEST_TEL));

        assertEquals("This screen name hss already existed", e.getMessage());
    }
}

