package com.example.demo.application.usergroup;

import java.util.List;

public interface UserGroupQueryService {

    public List<UserGroupDTO> selectUserGroupByCreatedBy(String createdBy, int page, int per);

    public int selectUserGroupCountById(String createdBy);

    public UserGroupDTO selectUserGroupById(String userGroupId);
}
