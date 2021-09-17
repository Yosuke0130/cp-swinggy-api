package com.example.demo.application.usergroup;

import java.util.List;

public interface UserGroupQueryService {

    public List<UserGroupDTO> selectUserGroupById(String userId, int page, int per);

    public int selectUserGroupCountById(String userId);
}
