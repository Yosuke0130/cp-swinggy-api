package com.example.demo.application.usergroupmember;

import java.util.List;
import java.util.Optional;

public interface UserGroupMemberApplicationService {

    public List<UserGroupMemberQueryModel> getUserGroupMembers(String userGroupId, Optional<Integer> page, Optional<Integer> per) throws IllegalArgumentException;

    public int getUserGroupMemberCount(String userGroupId);

}
