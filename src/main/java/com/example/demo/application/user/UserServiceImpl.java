package com.example.demo.application.user;

import com.example.demo.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean createAccount(int userId, String name, String screenName, String email, String tel){
        try {
            userRepository.insert(userId, name, screenName, email, tel);
            System.out.println("hogehoge");
            return true;

        } catch (SQLException e) {

            return false;
        }
    }

}
