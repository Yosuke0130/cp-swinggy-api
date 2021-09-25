package com.example.demo.infrastructure.usergroup;

import com.example.demo.application.usergroup.UserGroupException;
import com.example.demo.domain.usergroup.UserGroup;
import com.example.demo.domain.usergroup.UserGroupRepository;
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
    public void insert(UserGroup userGroup) throws UserGroupException{
        try {
            jdbc.update("INSERT INTO user_group(group_id, group_name, owner) VALUES(?, ?, ?)",
                    userGroup.getUserGroupId(),
                    userGroup.getUserGroupName(),
                    userGroup.getOwner());

            UUID belongingId = UUID.randomUUID();
            jdbc.update("INSERT INTO group_user_belonging(belonging_id, group_id, member) VALUES(?, ?, ?)",
                    belongingId.toString(),
                    userGroup.getUserGroupId(),
                    userGroup.getOwner());

        } catch (DataAccessException e) {
            throw new UserGroupException("DB access error occurred when registering new user_group.", e);
        }
    }

    @Override
    @Transactional
    public void update(UserGroup userGroup) throws UserGroupException{
        try {
            jdbc.update("UPDATE user_group SET group_name = ? WHERE group_id = ?",
                    userGroup.getUserGroupName(),
                    userGroup.getUserGroupId());

        } catch (DataAccessException e) {
            throw new UserGroupException("DB access error occurred when updating userGroupName.", e);
        }
    }

    @Override
    @Transactional
    public void delete(UserGroup userGroup) throws UserGroupException{
        try {
            jdbc.update("DELETE FROM group_user_belonging WHERE group_id = ?", userGroup.getUserGroupId());
            jdbc.update("DELETE FROM user_group WHERE group_id = ?", userGroup.getUserGroupId());
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new UserGroupException("DB access error occurred when deleting userGroup", e);
        }
    }

}
