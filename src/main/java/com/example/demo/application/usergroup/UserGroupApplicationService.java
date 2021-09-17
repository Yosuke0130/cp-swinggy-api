package com.example.demo.application.usergroup;

import java.util.List;
import java.util.Optional;

public interface UserGroupApplicationService {

    public void createUserGroup(String groupName, String createdBy) throws IllegalStateException, IllegalArgumentException, GroupException;

    public List<UserGroupDTO> getUserGroups(String userId, Optional<Integer> page, Optional<Integer> per) throws IllegalArgumentException;

    public int getUserGroupCount(String userId);
}
