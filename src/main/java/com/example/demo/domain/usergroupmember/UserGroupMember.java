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

    public String getUserGroupMemberId() {return userGroupMemberId.getValue();}

    public String getUserGroupId() {return userGroupId;}

    public String getUserId() {return userId;}
}
