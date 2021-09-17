package com.example.demo.application.usergroup;

public class UserGroupDTO {

    private String userGroupId;
    private String createdBy;
    private String UserGroupName;

    public UserGroupDTO(String userGroupId, String createdBy, String userGroupName) {
        this.userGroupId = userGroupId;
        this.createdBy = createdBy;
        this.UserGroupName = userGroupName;
    }

    public String getUserGroupId() {return  this.userGroupId;}
    public String getCreatedBy() {return this.createdBy;}
    public String getUserGroupName() {return  this.UserGroupName;}
}
