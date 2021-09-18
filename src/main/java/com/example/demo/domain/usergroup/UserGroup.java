package com.example.demo.domain.usergroup;

public class UserGroup {

    private UserGroupId userGroupId;
    private UserGroupName userGroupName;
    private String createdBy;

    public UserGroup(String userGroupName, String createdBy) throws IllegalArgumentException{
        this.userGroupName = new UserGroupName(userGroupName);
        this.userGroupId = new UserGroupId();
        this.createdBy = createdBy;
    }

    public UserGroup(String userGroupId, String userGroupName, String createdBy) {
        this.userGroupName = new UserGroupName(userGroupName);
        this.userGroupId = new UserGroupId(userGroupId);
        this.createdBy = createdBy;
    }

    public String getUserGroupId() {return this.userGroupId.getValue();}
    public String getUserGroupName() {return this.userGroupName.getValue();}
    public String getCreatedBy() {return this.createdBy;}

    public void changeUserGroupName(String userGroupName) throws IllegalArgumentException{
        this.userGroupName = new UserGroupName(userGroupName);
    }

}
