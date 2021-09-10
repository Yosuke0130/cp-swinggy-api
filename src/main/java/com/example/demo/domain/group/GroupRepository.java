package com.example.demo.domain.group;

public interface GroupRepository {

    public int selectGroupByGroupName(String groupName);

    public void insertGroup(Group group);
}
