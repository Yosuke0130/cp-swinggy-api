package com.example.demo.presentation.usergroupmember;

import com.example.demo.application.usergroupmember.UserGroupMemberQueryModel;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserGroupMemberResource {
    private String id;
    private String userGroupId;
    private String userId;
    private boolean isOwner;

    public UserGroupMemberResource(UserGroupMemberQueryModel userGroupMember) {
        this.id = userGroupMember.getUserGroupMemberId();
        this.userGroupId = userGroupMember.getUserGroupId();
        this.userId = userGroupMember.getUserId();
        this.isOwner = userGroupMember.getIsOwner();
    }

    public String getId() {
        return id;
    }

    public String getUserGroupId() {
        return userGroupId;
    }

    public String getUserId() {
        return userId;
    }

    public boolean getIsOwner() { return isOwner;}
}
