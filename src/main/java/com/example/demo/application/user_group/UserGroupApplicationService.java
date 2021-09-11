package com.example.demo.application.user_group;

public interface UserGroupApplicationService {

    public void createUserGroup(String groupName, String createdBy) throws IllegalStateException, IllegalArgumentException, GroupException;

}
