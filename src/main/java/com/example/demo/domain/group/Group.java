package com.example.demo.domain.group;

public class Group {

    private GroupId groupId;
    private GroupName groupName;
    private String createdBy;

    public Group(String groupName, String createdBy) throws IllegalArgumentException{
        this.groupId = new GroupId();
        this.groupName = new GroupName(groupName);
        this.createdBy = createdBy;
    }

    public String getGroupId() {return this.groupId.getValue();}
    public String getGroupName() {return this.groupName.getValue();}
    public String getCreatedBy() {return this.createdBy;}

}
