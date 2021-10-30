package com.example.demo.presentation.usergroupthread;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserGroupCommentListResource {
    private List<UserGroupCommentResource> userGroupComments;
    private int total;

    public UserGroupCommentListResource(List<UserGroupCommentResource> userGroupComments, int total) {
        this.userGroupComments = userGroupComments;
        this.total = total;
    }

    public List<UserGroupCommentResource> getUserGroupComments() {
        return userGroupComments;
    }

    public int getTotal() {
        return total;
    }
}
