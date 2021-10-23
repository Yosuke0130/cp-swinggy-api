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

    public String getUserGroupMemberId() {return this.userGroupMemberId;}

    public String getUserGroupId() {return this.userGroupId;}

    public String getUserId() {return this.userId;}
}
