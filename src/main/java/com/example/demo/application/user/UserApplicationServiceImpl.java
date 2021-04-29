package com.example.demo.application.user;

import com.example.demo.Logging;
import com.example.demo.domain.user.User;
import com.example.demo.domain.user.UserRepository;
import com.example.demo.domain.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserApplicationServiceImpl implements UserApplicationService {

    @Autowired
    Logging logger;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;


    @Override
    public void create(int userId, String firstName, String lastName, String screenName, byte[] profileImage, String email, String tel)
            throws UserCreateException, IllegalArgumentException{

        User user = new User(userId, firstName, lastName, screenName, email, tel);

        //ドメインサービス 重複チェッククラス
        boolean telResult = userService.existsTel(user);
        boolean emailResult = userService.existsEmail(user);
        boolean screenNameResult = userService.existsScreenName(user);

        if(telResult && emailResult && screenNameResult) {

            logger.debug("insert user data to db");
            userRepository.insert(user, profileImage);

        } else if(!telResult) {

            throw new IllegalArgumentException("This tel number has already existed");

        } else if(!emailResult) {

            throw new IllegalArgumentException("This email address has already existed");

        } else if(!screenNameResult) {

            throw new IllegalArgumentException("This screen name hss already existed");

        }
    }
}
