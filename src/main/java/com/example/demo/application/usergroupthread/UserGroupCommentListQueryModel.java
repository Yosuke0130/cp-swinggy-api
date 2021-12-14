package com.example.demo.application.usergroupthread;

import java.util.List;

public class UserGroupCommentListQueryModel {

    private List<UserGroupCommentQueryModel> userGroupCommentQueryModels;
    private int count;

    public UserGroupCommentListQueryModel(List<UserGroupCommentQueryModel> userGroupCommentQueryModels, int count) {
        this.userGroupCommentQueryModels = userGroupCommentQueryModels;
        this.count = count;
    }

    public List<UserGroupCommentQueryModel> getUserGroupCommentQueryModels() {
        return userGroupCommentQueryModels;
    }

    public int getCount() {
        return count;
    }
}
