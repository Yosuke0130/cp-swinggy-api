package com.example.demo.infrastructure.usergroupthread;

import com.example.demo.application.usergroupthread.UserGroupThreadException;
import com.example.demo.domain.usergroupthread.UserGroupThread;
import com.example.demo.domain.usergroupthread.UserGroupThreadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcUserGroupThreadRepository implements UserGroupThreadRepository {

    @Autowired
    private JdbcTemplate jdbc;

    @Override
    public void insert(UserGroupThread thread) throws UserGroupThreadException {
        try {
            jdbc.update("INSERT INTO user_group_thread(user_group_thread_id, group_id, name) VALUES(?, ?, ?)",
                    thread.getId(), thread.getUserGroupId(), thread.getName());
        } catch (DataAccessException e) {
            throw new UserGroupThreadException("Insert userGroupThread failed.");
        }
    }

    @Override
    public void updateName(UserGroupThread thread) {
        try {
            jdbc.update("UPDATE user_group_thread SET name = ? WHERE user_group_thread_id = ?", thread.getName(), thread.getId());
        } catch (DataAccessException e) {
            throw new UserGroupThreadException("DB access error when updating threadName.");
        }
    }

    @Override
    public void delete(UserGroupThread thread) {
        try {
            jdbc.update("DELETE FROM user_group_thread WHERE user_group_thread_id = ?", thread.getId());
        } catch (DataAccessException e) {
            throw new UserGroupThreadException("DB access error when deleting thread: " + thread.getId());
        }
    }

}
