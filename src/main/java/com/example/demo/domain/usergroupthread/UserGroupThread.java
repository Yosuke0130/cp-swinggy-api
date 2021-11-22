package com.example.demo.domain.usergroupthread;

public class UserGroupThread {

    private UserGroupThreadId id;
    private String userGroupId;
    private UserGroupThreadName name;

    public UserGroupThread(String userGroupId, String name) throws IllegalArgumentException {
        this.id = new UserGroupThreadId();
        this.name = new UserGroupThreadName(name);
        this.userGroupId = userGroupId;
    }

    public UserGroupThread(String userGroupId, String id, String name) throws IllegalArgumentException {
        this.id = new UserGroupThreadId(id);
        this.name = new UserGroupThreadName(name);
        this.userGroupId = userGroupId;
    }

    public String getId() {return this.id.getValue();}
    public String getUserGroupId() {return this.userGroupId;}
    public String getName() {return this.name.getValue();}

}
