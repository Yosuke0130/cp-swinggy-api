package com.example.demo.application.usergroup;

import java.util.List;

public interface UserGroupQueryService {

    public List<UserGroupQueryModel> selectUserGroupByUserId(String userId, int page, int per);

    public int selectUserGroupCountByUserId(String userId);

    public UserGroupQueryModel selectUserGroupByGroupId(String userGroupId) throws UserGroupException;
}
