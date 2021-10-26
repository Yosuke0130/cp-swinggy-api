package com.example.demo.domain.usergroup;

import com.example.demo.application.usergroup.UserGroupException;
import com.example.demo.domain.usergroupmember.UserGroupMember;

public interface UserGroupRepository {

    public void insert(UserGroup userGroup, UserGroupMember userGroupMember) throws UserGroupException;

    public void update(UserGroup userGroup) throws UserGroupException;

    public void delete(UserGroup userGroup) throws UserGroupException;

}
