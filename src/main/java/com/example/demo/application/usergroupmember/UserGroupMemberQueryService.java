package com.example.demo.application.usergroupmember;

import java.util.List;

public interface UserGroupMemberQueryService {

    public List<UserGroupMemberQueryModel> selectUserGroupMembersByUserGroupId(String userGroupId, int page, int per);

    public int selectUserGroupMemberCount(String groupId);

}
