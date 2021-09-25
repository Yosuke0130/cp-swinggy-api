package com.example.demo.application.user;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface UserApplicationService {

    public void create(String userId, String firstName, String lastName, String screenName, Optional<MultipartFile> profileImage, String email, Optional<String> tel) throws UserCreateException, IllegalStateException,IllegalArgumentException;

    public UserModel get(String userId) throws UserCreateException, IllegalStateException;

    public List<UserModel> getUsers(int page, int per, Optional<String> userGroupId) throws UserCreateException;

    public int getCount(Optional<String> userGroupId);

    public int getCountByScreenName(String screenName) throws UserCreateException;

}
