package com.example.demo.application.user;


public interface UserApplicationService {

    public void create(int userId, String firstName, String lastName, String screenName, byte[] profileImage, String email, String tel) throws UserCreateException;

}
