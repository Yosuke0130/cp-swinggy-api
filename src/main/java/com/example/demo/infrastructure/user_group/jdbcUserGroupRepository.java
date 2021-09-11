package com.example.demo.infrastructure.user_group;

import com.example.demo.application.user_group.GroupException;
import com.example.demo.domain.user_group.UserGroup;
import com.example.demo.domain.user_group.UserGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public class jdbcUserGroupRepository implements UserGroupRepository {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    @Transactional
    public void insertUserGroup(UserGroup userGroup) throws DataAccessException{
        try {
            jdbc.update("INSERT INTO user_group(group_id, group_name, created_by) VALUES(?, ?, ?)",
                    userGroup.getUserGroupId(),
                    userGroup.getUserGroupName(),
                    userGroup.getCreatedBy());

            // insert data to group user belonging
            UUID belongingId = UUID.randomUUID();
            jdbc.update("INSERT INTO group_user_belonging(belonging_id, group_id, user_id) VALUES(?, ?, ?)",
                    belongingId.toString(),
                    userGroup.getUserGroupId(),
                    userGroup.getCreatedBy());

        } catch (DataAccessException e) {
            throw new GroupException("DB access error occurred when registering new user_group.", e);
        }
    }

}
