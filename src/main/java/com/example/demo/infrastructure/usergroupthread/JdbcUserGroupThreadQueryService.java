package com.example.demo.infrastructure.usergroupthread;

import com.example.demo.application.usergroupthread.UserGroupThreadListQueryModel;
import com.example.demo.application.usergroupthread.UserGroupThreadQueryModel;
import com.example.demo.application.usergroupthread.UserGroupThreadQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class JdbcUserGroupThreadQueryService implements UserGroupThreadQueryService {

    @Autowired
    JdbcTemplate jdbc;

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
}
