package com.example.demo.domain.usergroupmember;

public class UserGroupMember {

    private UserGroupMemberId userGroupMemberId;
    private String userGroupId;
    private String userId;

    public UserGroupMember(String userGroupId, String userId) {
        this.userGroupMemberId = new UserGroupMemberId();
        this.userGroupId = userGroupId;
        this.userId = userId;
    }

    public UserGroupMemberId getUserGroupMemberId() {return userGroupMemberId;}

    public String getUserGroupId() {return userGroupId;}

    public String getUserId() {return userId;}
}
