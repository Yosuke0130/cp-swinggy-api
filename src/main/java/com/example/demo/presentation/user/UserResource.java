package com.example.demo.presentation.user;

import com.example.demo.application.user.UserModel;

public class UserResource {

    private int user_id;
    private String first_name;
    private String last_name;
    private String screen_name;
    private String email;
    private String tel;
    private String profile_image_url;

    public UserResource(UserModel userModel) {
        this.user_id = userModel.getUserId();
        this.first_name = userModel.getFirstName();
        this.last_name = userModel.getLastName();
        this.screen_name = userModel.getScreenName();
        this.email = userModel.getEmail();
        this.tel = userModel.getTel();
        this.profile_image_url = userModel.getProfileImageURL().toString();
    }

    public int getUser_id() {return user_id;}
    public String getFirst_name() {return first_name;}
    public String getLast_name() {return last_name;}
    public String getScreen_name() {return screen_name;}
    public String getEmail() {return email;}
    public String getTel() {return tel;}
    public String getProfile_image_url() {return profile_image_url;}

}
