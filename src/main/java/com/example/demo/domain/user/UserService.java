package com.example.demo.domain.user;

import com.example.demo.Logging;
import com.example.demo.application.user.UserCreateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    Logging logger;

    @Autowired
    UserRepository userRepository;

    //screenName, email, telの重複をチェック
    public boolean telExists(User user) {
        List<Map<String, Object>> users = null;
        try {
            users = userRepository.selectByTel(user);
        } catch (DataAccessException e) {
            throw new UserCreateException("Failed to access the data source.");
        }
        if (users == null) {
            throw new UserCreateException("Unexpected null value was returned from UserRepository.");
        }
        return users.size() > 0;
    }

    public boolean emailExists(User user) {
        try {
            List<Map<String, Object>> userSelectedByEmail = userRepository.selectByEmail(user);
            logger.debug("Existed user data: " +userSelectedByEmail.get(0));

        } catch (Exception e) {
            return false;
        }
        return  true;
    }

    public boolean screenNameExists(User user) {
        try {
            List<Map<String, Object>> userSelectedByScreenName = userRepository.selectByScreenName(user);
            logger.debug("Existed user data: " + userSelectedByScreenName.get(0));

        } catch (Exception e) {
            return false;
        }
        return  true;
    }
}
