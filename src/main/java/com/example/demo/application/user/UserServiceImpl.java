package com.example.demo.application.user;

import com.example.demo.Logging;
import com.example.demo.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    Logging logger;

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean createAccount(int userId, String name, String screenName, String email, String tel){
        try {
            logger.debug("insert user data to db");
            boolean createResult = userRepository.insert(userId, name, screenName, email, tel);

            return createResult;

        } catch (SQLException e) {
            logger.error("error code : " + e.getErrorCode());

            return false;
        }
    }

}
