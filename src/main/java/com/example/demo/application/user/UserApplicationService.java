package com.example.demo.application.user;


import java.util.Optional;

public interface UserApplicationService {

    public void create(int userId, String firstName, String lastName, String screenName, Optional<byte[]> profileImage, String email, String tel) throws UserCreateException;

}
