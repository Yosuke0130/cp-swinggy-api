package com.example.demo.domain.usergroupmember;

public class UserGroupMember {

    private UserGroupMemberId userGroupMemberId;
    private String userGroupId;
    private String userId;
    private boolean isOwner;

    public UserGroupMember(String userGroupId, String userId, boolean isOwner) {
        this.userGroupMemberId = new UserGroupMemberId();
        this.userGroupId = userGroupId;
        this.userId = userId;
        this.isOwner = isOwner;
    }

    public String getUserGroupMemberId() {return userGroupMemberId.getValue();}

    public String getUserGroupId() {return userGroupId;}

    public String getUserId() {return userId;}

    public boolean getIsOwner() {return isOwner;}
}
