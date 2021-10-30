package com.example.demo.presentation.usergroupthread;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserGroupThreadListResource {
    private List<UserGroupThreadResource> userGroupThreads;
    private int total;

    public UserGroupThreadListResource(List<UserGroupThreadResource> userGroupThreads, int total) {
        this.userGroupThreads = userGroupThreads;
        this.total = total;
    }

    public List<UserGroupThreadResource> getUserGroupThreads() {
        return userGroupThreads;
    }

    public int getTotal() {
        return total;
    }
}
