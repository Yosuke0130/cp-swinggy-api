package com.example.demo.application.user;

import java.net.URL;

public class UserModel {

    private String userId;
    private String firstName;
    private String lastName;
    private String screenName;
    private String email;
    private String tel;
    private URL profileImageURL;


    public UserModel(String userId, String firstName, String lastName, String screenName, String email, String tel, URL profileImageURL) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.screenName = screenName;
        this.email = email;
        this.tel = tel;
        this.profileImageURL = profileImageURL;
    }


    public String getUserId() {return userId;}
    public String getFirstName() {return firstName;}
    public String getLastName() {return lastName;}
    public String getScreenName() {return screenName;}
    public String getEmail() {return email;}
    public String getTel() {return tel;}
    public URL getProfileImageURL() {return profileImageURL;}
}
