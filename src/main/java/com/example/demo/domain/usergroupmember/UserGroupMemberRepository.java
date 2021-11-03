package com.example.demo.domain.usergroupmember;

import com.example.demo.application.usergroupmember.UserGroupMemberException;

public interface UserGroupMemberRepository {

    public void insertUserGroupMember(UserGroupMember userGroupMember) throws UserGroupMemberException;

    public void deleteUserGroupMember(UserGroupMember userGroupMember) throws UserGroupMemberException;

    public void deleteUserGroupMembersByGroupId(String userGroupId) throws UserGroupMemberException;

}
