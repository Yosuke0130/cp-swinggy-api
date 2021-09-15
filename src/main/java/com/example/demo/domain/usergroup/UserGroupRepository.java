package com.example.demo.domain.usergroup;

import org.springframework.dao.DataAccessException;

public interface UserGroupRepository {

    public void insertUserGroup(UserGroup userGroup) throws DataAccessException;

}
