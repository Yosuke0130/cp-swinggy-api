package com.example.demo.infrastructure.usergroupthread;

import com.example.demo.application.usergroupthread.UserGroupThreadException;
import com.example.demo.application.usergroupthread.UserGroupThreadListQueryModel;
import com.example.demo.application.usergroupthread.UserGroupThreadQueryModel;
import com.example.demo.application.usergroupthread.UserGroupThreadQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class JdbcUserGroupThreadQueryService implements UserGroupThreadQueryService {

    @Autowired
    private JdbcTemplate jdbc;

    public UserGroupThreadListQueryModel selectList(String userGroupId, int page, int per) {

        int offset = 0;
        if(page > 0) {offset = page * per;}

        List<Map<String, Object>> threads = jdbc.queryForList(
                "SELECT * FROM user_group_thread WHERE group_id = ? ORDER BY name DESC LIMIT ? OFFSET ?",
                userGroupId, per, offset);

        Integer count = jdbc.queryForObject(
                "SELECT COUNT(*) FROM user_group_thread WHERE group_id = ?",
                Integer.class, userGroupId);

        List<UserGroupThreadQueryModel> threadQueryModelList = threads.stream()
                .map(thread -> convertToUserGroupThreadQueryModel(thread))
                .collect(Collectors.toList());

        UserGroupThreadListQueryModel userGroupThreadListQueryModel =
                new UserGroupThreadListQueryModel(threadQueryModelList, count);

        return userGroupThreadListQueryModel;
    }

    private UserGroupThreadQueryModel convertToUserGroupThreadQueryModel(Map<String, Object> thread) {
        return new UserGroupThreadQueryModel(
                (String)thread.get("user_group_thread_id"),
                (String)thread.get("name"),
                (String)thread.get("group_id"));
    }

    @Override
    public boolean exists(String userGroupId, String userGroupThreadId) throws UserGroupThreadException{
        try {
            String groupId = jdbc.queryForObject("SELECT group_id FROM user_group_thread WHERE user_group_thread_id = ?", String.class, userGroupThreadId);

            return groupId.equals(userGroupId);

        } catch (IncorrectResultSizeDataAccessException e) {
            return false;
        } catch (DataAccessException e) {
            throw new UserGroupThreadException("DB access error when checking if userGroupThread exists.");
        }
    }

    @Override
    public UserGroupThreadQueryModel selectThreadById(String userGroupThreadId) {

        Map<String, Object> thread = jdbc.queryForMap("SELECT * FROM user_group_thread WHERE user_group_thread_id = ?", userGroupThreadId);
        UserGroupThreadQueryModel userGroupThreadQueryModel = convertToUserGroupThreadQueryModel(thread);

        return userGroupThreadQueryModel;
    }

}


