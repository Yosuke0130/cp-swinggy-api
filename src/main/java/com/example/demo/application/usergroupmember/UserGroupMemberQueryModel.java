package com.example.demo.application.usergroupmember;

public class UserGroupMemberQueryModel {

    private String userGroupMemberId;
    private String userGroupId;
    private String userId;

    public UserGroupMemberQueryModel(String userGroupMemberId, String userGroupId, String userId) {
        this.userGroupMemberId = userGroupMemberId;
        this.userGroupId = userGroupId;
        this.userId = userId;
    }

    public String getUserGroupMemberId() {return userGroupMemberId;}

    public String getUserGroupId() {return userGroupId;}

    public String getUserId() {return userId;}
}
