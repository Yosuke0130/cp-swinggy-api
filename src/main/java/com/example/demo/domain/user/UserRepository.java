package com.example.demo.domain.user;

import com.example.demo.application.user.UserCreateException;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface UserRepository {

    public void insert(User user, Optional<MultipartFile> profileImage) throws UserCreateException;

    public int selectTel(User user);

    public int selectEmail(User user);

    public int selectScreenName(User user);
}
