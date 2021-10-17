package com.example.demo.presentation.usergroupmember;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserGroupMemberListResource {
    private List<UserGroupMemberResource> userGroupMembers;
    private int total;

    public List<UserGroupMemberResource> getUserGroupMembers() {
        return userGroupMembers;
    }

    public int getTotal() {
        return total;
    }
}
