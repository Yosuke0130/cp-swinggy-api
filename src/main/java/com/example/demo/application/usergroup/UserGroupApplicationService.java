package com.example.demo.application.usergroup;

import java.util.List;
import java.util.Optional;

public interface UserGroupApplicationService {

    public void createUserGroup(String groupName, String owner) throws IllegalStateException, IllegalArgumentException, UserGroupException;

    public List<UserGroupQueryModel> getBelongedUserGroups(String member, Optional<Integer> page, Optional<Integer> per) throws IllegalArgumentException;

    public int getBelongedUserGroupCount(String member);

    public UserGroupQueryModel getUserGroup(String userGroupId);

    public void changeUserGroupName(String userGroupId, String userGroupName) throws UserGroupException, IllegalArgumentException;

    public void deleteUserGroup(String groupId) throws UserGroupException, IllegalArgumentException;
}
