package com.example.demo.domain.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public boolean telExists(User user) {

        //screenName, email, telの重複をチェック
        int selectTel = userRepository.selectTel(user);
        if (selectTel >= 1) {
            return true;
        }
        return false;
    }

    public boolean emailExists(User user) {

        int selectEmail = userRepository.selectEmail(user);
        if (selectEmail >= 1) {
            return true;
        }
        return false;
    }

    public boolean screenNameExists(User user) {
        int selectScreenName = userRepository.selectScreenName(user);
        if(selectScreenName >= 1) {
            return true;
        }
        return false;
    }

}
