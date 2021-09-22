package com.example.demo.application.usergroup;

public class UserGroupQueryModel {

    private String userGroupId;
    private String owner;
    private String UserGroupName;

    public UserGroupQueryModel(String userGroupId, String owner, String userGroupName) {
        this.userGroupId = userGroupId;
        this.owner = owner;
        this.UserGroupName = userGroupName;
    }

    public String getUserGroupId() {return  this.userGroupId;}
    public String getOwner() {return this.owner;}
    public String getUserGroupName() {return  this.UserGroupName;}
}
