package com.example.demo.presentation.usergroup;

import com.example.demo.application.usergroup.UserGroupQueryModel;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserGroupResource {

    private String groupId;
    private String owner;
    private String groupName;

    public UserGroupResource(UserGroupQueryModel userGroup) {
        this.groupId = userGroup.getUserGroupId();
        this.owner = userGroup.getOwner();
        this.groupName = userGroup.getUserGroupName();
    }

    public String getGroupId() {return this.groupId;}
    public String getOwner() {return this.owner;}
    public String getGroupName() {return this.groupName;}

}
