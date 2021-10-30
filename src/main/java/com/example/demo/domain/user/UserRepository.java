package com.example.demo.domain.user;

import com.example.demo.application.user.UserCreateException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserRepository {

    public void insert(User user, Optional<MultipartFile> profileImage) throws UserCreateException, IllegalStateException;

    public List<User> selectByTel(User user);

    public List<User> selectByEmail(User user);

    public List<User> selectByScreenName(User user);

    public User select(String userId) throws UserCreateException, IllegalStateException;

    public List<User> selectUsers(int page, int per) throws UserCreateException;

    public List<User> selectUsersByGroupId(int page, int per, String userGroupId) throws UserCreateException;

    public int selectCount();

    public int selectCountByGroupId(String userGroupId);

    public int selectCountByScreenName(String screenName) throws UserCreateException;

    public boolean exists(String userId);
}
