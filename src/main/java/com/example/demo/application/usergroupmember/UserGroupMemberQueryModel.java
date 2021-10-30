package com.example.demo.application.usergroupmember;

public class UserGroupMemberQueryModel {

    private String userGroupMemberId;
    private String userGroupId;
    private String userId;
    private boolean isOwner;

    public UserGroupMemberQueryModel(String userGroupMemberId, String userGroupId, String userId, boolean isOwner) {
        this.userGroupMemberId = userGroupMemberId;
        this.userGroupId = userGroupId;
        this.userId = userId;
        this.isOwner = isOwner;
    }

    public String getUserGroupMemberId() {return this.userGroupMemberId;}

    public String getUserGroupId() {return this.userGroupId;}

    public String getUserId() {return this.userId;}

    public boolean getIsOwner() {return this.isOwner;}
}
