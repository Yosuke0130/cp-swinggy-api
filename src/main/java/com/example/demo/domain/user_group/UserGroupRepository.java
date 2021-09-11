package com.example.demo.domain.user_group;

import org.springframework.dao.DataAccessException;

public interface UserGroupRepository {

    public void insertUserGroup(UserGroup userGroup) throws DataAccessException;

}
