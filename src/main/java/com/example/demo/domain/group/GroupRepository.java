package com.example.demo.domain.group;

import org.springframework.dao.DataAccessException;

public interface GroupRepository {

    public void insertGroup(Group group) throws DataAccessException;

}
