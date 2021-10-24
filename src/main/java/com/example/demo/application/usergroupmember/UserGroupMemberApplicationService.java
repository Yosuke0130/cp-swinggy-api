package com.example.demo.application.usergroupmember;

import java.util.Optional;

public interface UserGroupMemberApplicationService {

    public UserGroupMemberListQueryModel getUserGroupMembers(String userGroupId, Optional<Integer> page, Optional<Integer> per) throws IllegalArgumentException;

}
