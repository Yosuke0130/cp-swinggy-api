package com.example.demo.presentation.usergroupmember;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserGroupMemberResource {
    private String id;
    private String userGroupId;
    private String userId;

    public String getId() {
        return id;
    }

    public String getUserGroupId() {
        return userGroupId;
    }

    public String getUserId() {
        return userId;
    }
}
