package com.example.demo.presentation.usergroupthread;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserGroupThreadResource {
    private String id;
    private String name;
    private String userGroupId;

    public UserGroupThreadResource(String id, String name, String userGroupId) {
        this.id = id;
        this.name = name;
        this.userGroupId = userGroupId;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUserGroupId() {
        return userGroupId;
    }
}
