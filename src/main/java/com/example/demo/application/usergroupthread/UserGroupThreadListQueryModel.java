package com.example.demo.application.usergroupthread;

import java.util.List;

public class UserGroupThreadListQueryModel {

    private List<UserGroupThreadQueryModel> userGroupThreadQueryModels;
    private int count;

    public UserGroupThreadListQueryModel(List<UserGroupThreadQueryModel> userGroupThreadQueryModels, int count) {
        this.userGroupThreadQueryModels = userGroupThreadQueryModels;
        this.count = count;
    }

    public List<UserGroupThreadQueryModel> getUserGroupThreadQueryModels() {return userGroupThreadQueryModels;}

    public int getCount() {return count;}
}
