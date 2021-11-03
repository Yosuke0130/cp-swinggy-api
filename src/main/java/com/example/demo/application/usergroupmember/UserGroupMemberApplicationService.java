package com.example.demo.application.usergroupmember;

import java.util.Optional;

public interface UserGroupMemberApplicationService {

    public UserGroupMemberListQueryModel getUserGroupMembers(String userGroupId, Optional<Integer> page, Optional<Integer> per) throws IllegalArgumentException;

    public void registerUserGroupMember(String userGroupId, String userId) throws UserGroupMemberException, IllegalArgumentException;

    public void deleteUserGroupMember(String userGroupMemberId) throws UserGroupMemberException, IllegalArgumentException;

}
