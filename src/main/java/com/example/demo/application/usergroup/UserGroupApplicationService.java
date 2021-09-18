package com.example.demo.application.usergroup;

import java.util.List;
import java.util.Optional;

public interface UserGroupApplicationService {

    public void createUserGroup(String groupName, String createdBy) throws IllegalStateException, IllegalArgumentException, UserGroupException;

    public List<UserGroupDTO> getOwnedUserGroups(String createdBy, Optional<Integer> page, Optional<Integer> per) throws IllegalArgumentException;

    public int getOwnedUserGroupCount(String createdBy);

    public UserGroupDTO getUserGroup(String userGroupId);

    public void changeUserGroupName(String userGroupId, String userGroupName) throws UserGroupException, IllegalArgumentException;
}
