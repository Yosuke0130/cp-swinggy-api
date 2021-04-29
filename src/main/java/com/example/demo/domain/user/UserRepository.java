package com.example.demo.domain.user;

import com.example.demo.application.user.UserCreateException;

public interface UserRepository {

    public void insert(User user, byte[] profileImage) throws UserCreateException;

    public int selectTel(User user);

    public int selectEmail(User user);

    public int selectScreenName(User user);
}
