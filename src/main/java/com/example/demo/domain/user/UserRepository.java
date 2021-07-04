package com.example.demo.domain.user;

import com.example.demo.application.user.UserCreateException;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserRepository {

    public void insert(User user, Optional<MultipartFile> profileImage) throws UserCreateException, IllegalStateException;

    public List<Map<String, Object>> selectByTel(User user);

    public List<Map<String, Object>> selectByEmail(User user);

    public List<Map<String, Object>> selectByScreenName(User user);

    public User select(String userId) throws UserCreateException, IllegalStateException;

    public List<User> selectUsers(int page, int per) throws UserCreateException;

    public int selectCount();

    public int selectCountByScreenName(String screenName);
}
