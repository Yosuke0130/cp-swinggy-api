package com.example.demo.domain.usergroup;

public class UserGroup {

    private UserGroupId userGroupId;
    private UserGroupName userGroupName;
    private String owner;

    public UserGroup(String userGroupName, String owner) throws IllegalArgumentException{
        this.userGroupName = new UserGroupName(userGroupName);
        this.userGroupId = new UserGroupId();
        this.owner = owner;
    }

    public UserGroup(String userGroupId, String userGroupName, String owner) {
        this.userGroupName = new UserGroupName(userGroupName);
        this.userGroupId = new UserGroupId(userGroupId);
        this.owner = owner;
    }

    public String getUserGroupId() {return this.userGroupId.getValue();}
    public String getUserGroupName() {return this.userGroupName.getValue();}
    public String getOwner() {return this.owner;}

    public void changeUserGroupName(String userGroupName) throws IllegalArgumentException{
        this.userGroupName = new UserGroupName(userGroupName);
    }

}
