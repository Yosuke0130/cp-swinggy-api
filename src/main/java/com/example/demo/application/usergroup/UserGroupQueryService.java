package com.example.demo.application.usergroup;

import java.util.List;

public interface UserGroupQueryService {

    public List<UserGroupDTO> selectUserGroupByUserId(String userId, int page, int per);

    public int selectUserGroupCountByUserId(String userId);

    public UserGroupDTO selectUserGroupByGroupId(String userGroupId) throws UserGroupException;
}
