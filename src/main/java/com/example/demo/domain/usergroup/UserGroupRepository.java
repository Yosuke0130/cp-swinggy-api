package com.example.demo.domain.usergroup;

import com.example.demo.application.usergroup.UserGroupException;

public interface UserGroupRepository {

    public void insertUserGroup(UserGroup userGroup) throws UserGroupException;

    public void updateUserGroupName(UserGroup userGroup) throws UserGroupException;

    public void deleteUserGroup(UserGroup userGroup) throws UserGroupException;

}
