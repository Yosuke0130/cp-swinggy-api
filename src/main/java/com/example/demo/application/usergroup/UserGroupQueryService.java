package com.example.demo.application.usergroup;

import java.util.List;

public interface UserGroupQueryService {

    public List<UserGroupQueryModel> selectUserGroupByMemberId(String member, int page, int per);

    public int selectUserGroupCountByMemberId(String member);

    public UserGroupQueryModel selectUserGroupByGroupId(String userGroupId) throws UserGroupException;

    public boolean exists(String userGroupId);
}
