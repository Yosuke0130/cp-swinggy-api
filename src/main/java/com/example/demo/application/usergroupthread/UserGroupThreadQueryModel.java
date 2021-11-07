package com.example.demo.application.usergroupthread;

public class UserGroupThreadQueryModel {

    private String userGroupThreadId;
    private String name;
    private String userGroupId;

    public UserGroupThreadQueryModel(String userGroupThreadId, String name, String userGroupId) {
        this.userGroupThreadId = userGroupThreadId;
        this.name = name;
        this.userGroupId = userGroupId;
    }

    public String getUserGroupThreadId() {return userGroupThreadId;}

    public String getName() {return name;}

    public String getUserGroupId() {return userGroupId;}
}
