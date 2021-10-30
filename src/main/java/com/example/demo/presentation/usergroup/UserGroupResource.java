package com.example.demo.presentation.usergroup;

import com.example.demo.application.usergroup.UserGroupQueryModel;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserGroupResource {

    private String id;
    private String owner;
    private String name;

    public UserGroupResource(UserGroupQueryModel userGroup) {
        this.id = userGroup.getUserGroupId();
        this.owner = userGroup.getOwner();
        this.name = userGroup.getUserGroupName();
    }

    public String getId() {return this.id;}
    public String getOwner() {return this.owner;}
    public String getName() {return this.name;}

}
