package com.example.demo.application.user;

import java.net.URL;
import java.util.Optional;

public class UserModel {

    private String userId;
    private String firstName;
    private String lastName;
    private String screenName;
    private String email;
    private Optional<String> tel;
    private URL profileImageURL;

    public UserModel(String userId, String firstName, String lastName, String screenName, String email, Optional<String> tel, URL profileImageURL) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.screenName = screenName;
        this.email = email;
        this.tel = tel;
        this.profileImageURL = profileImageURL;
    }


    public String getUserId() {return this.userId;}
    public String getFirstName() {return this.firstName;}
    public String getLastName() {return this.lastName;}
    public String getScreenName() {return this.screenName;}
    public String getEmail() {return this.email;}
    public Optional<String> getTel() {return this.tel;}
    public URL getProfileImageURL() {return this.profileImageURL;}
}
