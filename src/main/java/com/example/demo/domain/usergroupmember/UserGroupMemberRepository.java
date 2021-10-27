package com.example.demo.domain.usergroupmember;

import org.springframework.dao.DataAccessException;

public interface UserGroupMemberRepository {

    public void insertUserGroupMember(UserGroupMember userGroupMember) throws DataAccessException;

}
