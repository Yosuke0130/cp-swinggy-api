package com.example.demo.application.group;

public interface GroupApplicationService {

    public void createGroup(String groupName, String createdBy) throws IllegalStateException, IllegalArgumentException, GroupException;

}
