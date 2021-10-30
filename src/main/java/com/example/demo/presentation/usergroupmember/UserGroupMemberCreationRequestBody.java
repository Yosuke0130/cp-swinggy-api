package com.example.demo.presentation.usergroupmember;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import javax.validation.constraints.NotNull;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserGroupMemberCreationRequestBody {
    @NotNull
    private String userGroupId;

    @NotNull
    private String userId;

    public String getUserGroupId() {
        return userGroupId;
    }

    public String getUserId() {
        return userId;
    }
}
