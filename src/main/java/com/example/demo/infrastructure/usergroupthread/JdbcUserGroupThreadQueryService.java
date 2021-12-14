package com.example.demo.infrastructure.usergroupthread;

import com.example.demo.application.usergroupthread.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class JdbcUserGroupThreadQueryService implements UserGroupThreadQueryService {

    @Autowired
    private JdbcTemplate jdbc;

    public UserGroupThreadListQueryModel selectList(String userGroupId, int page, int per) {

        int offset = 0;
        if (page > 0) {
            offset = page * per;
        }

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
                (String) thread.get("user_group_thread_id"),
                (String) thread.get("name"),
                (String) thread.get("group_id"));
    }

    @Override
    public boolean exists(String userGroupId, String userGroupThreadId) throws UserGroupThreadException {
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

    @Override
    @Transactional
    public UserGroupCommentListQueryModel selectCommentList(String threadId, int page, int per) {
        int offset = 0;
        if (page > 0) {
            offset = page * per;
        }

        List<Map<String, Object>> comments = jdbc.queryForList("SELECT * FROM user_group_comment WHERE user_group_thread_id = ? ORDER BY created_at DESC LIMIT ? OFFSET ?", threadId, per, offset);

        List<UserGroupCommentQueryModel> commentQueryModels = comments.stream()
                .map(comment -> convertToUserGroupCommentQueryModel(comment))
                .collect(Collectors.toList());

        Integer count = jdbc.queryForObject("SELECT COUNT(*) FROM user_group_comment WHERE user_group_thread_id = ?", Integer.class, threadId);

        return new UserGroupCommentListQueryModel(commentQueryModels, count);
    }

    private UserGroupCommentQueryModel convertToUserGroupCommentQueryModel(Map<String, Object> comment) {
        return new UserGroupCommentQueryModel(
                (String) comment.get("user_group_comment_id"),
                (String) comment.get("user_group_thread_id"),
                (String) comment.get("member_id"),
                (String) comment.get("text"),
                (Timestamp) comment.get("created_at"));
    }

}


