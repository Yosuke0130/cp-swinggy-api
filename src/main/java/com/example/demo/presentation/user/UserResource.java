package com.example.demo.presentation.user;

import com.example.demo.application.user.UserModel;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserResource {

    private String userId;
    private String firstName;
    private String lastName;
    private String screenName;
    private String email;
    private String tel;
    private String profileImageURL;

    public UserResource(UserModel userModel) {
        this.userId = userModel.getUserId();
        this.firstName = userModel.getFirstName();
        this.lastName = userModel.getLastName();
        this.screenName = userModel.getScreenName();
        this.email = userModel.getEmail();
        this.tel = userModel.getTel();
        this.profileImageURL = userModel.getProfileImageURL().toString();
    }

    public String getUser_id() {
        return userId;
    }

    public String getFirst_name() {
        return firstName;
    }

    public String getLast_name() {
        return lastName;
    }

    public String getScreen_name() {
        return screenName;
    }

    public String getEmail() {
        return email;
    }

    public String getTel() {
        return tel;
    }

    public String getProfile_image_url() {
        return profileImageURL;
    }

}
