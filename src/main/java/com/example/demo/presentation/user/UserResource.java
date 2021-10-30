package com.example.demo.presentation.user;

import com.example.demo.application.user.UserModel;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.Optional;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserResource {

    private String id;
    private String firstName;
    private String lastName;
    private String screenName;
    private String email;
    private String tel;
    private String profileImageURL;

    public UserResource(UserModel userModel) {
        this.id = userModel.getUserId();
        this.firstName = userModel.getFirstName();
        this.lastName = userModel.getLastName();
        this.screenName = userModel.getScreenName();
        this.email = userModel.getEmail();
        this.tel = userModel.getTel().isPresent() ? userModel.getTel().get() : null;
        this.profileImageURL = userModel.getProfileImageURL().toString();
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() { return lastName;}

    public String getScreenName() {
        return screenName;
    }

    public String getEmail() {
        return email;
    }

    public String getTel() {
        return tel;
    }

    public String getProfileImageUrl() {
        return profileImageURL;
    }

}
