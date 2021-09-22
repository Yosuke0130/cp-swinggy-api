package com.example.demo.domain.usergroup;

import com.example.demo.application.usergroup.UserGroupException;

public interface UserGroupRepository {

    public void insert(UserGroup userGroup) throws UserGroupException;

    public void update(UserGroup userGroup) throws UserGroupException;

    public void delete(UserGroup userGroup) throws UserGroupException;

}
