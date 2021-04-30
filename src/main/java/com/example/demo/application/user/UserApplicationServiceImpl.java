package com.example.demo.application.user;

import com.example.demo.Logging;
import com.example.demo.domain.user.User;
import com.example.demo.domain.user.UserRepository;
import com.example.demo.domain.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserApplicationServiceImpl implements UserApplicationService {

    @Autowired
    Logging logger;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Override
    public void create(int userId, String firstName, String lastName, String screenName, Optional<byte[]> profileImage, String email, String tel)
            throws UserCreateException, IllegalArgumentException {

        User user = new User(userId, firstName, lastName, screenName, email, tel);
        System.out.println("shoud not reach here");
        //ドメインサービス 重複チェッククラス
        if (userService.telExists(user)) {
            throw new IllegalArgumentException("This tel number has already existed");
        }
        if (userService.emailExists(user)) {
            throw new IllegalArgumentException(("This email address has already existed"));
        }
        if (userService.screenNameExists(user)) {
            throw new IllegalArgumentException("This screen name hss already existed");
        }

        logger.debug("insert user data into db & upload profileImage");
        userRepository.insert(user, profileImage);

    }
}
