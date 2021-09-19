package com.example.demo.infrastructure.usergroup;

import com.example.demo.application.usergroup.UserGroupException;
import com.example.demo.application.usergroup.UserGroupQueryModel;
import com.example.demo.application.usergroup.UserGroupQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Repository
public class jdbcUserGroupQueryService implements UserGroupQueryService {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public List<UserGroupQueryModel> selectUserGroupByUserId(String userId, int page, int per) {
        int offset = 0;
        if(page > 0) {offset = page * per;}

        List<Map<String, Object>> userGroups = jdbc.queryForList(
                "SELECT * FROM user_group WHERE group_id IN (SELECT group_id FROM group_user_belonging WHERE user_id = ?)",
                userId);

        List<UserGroupQueryModel> userGroupQueryModels = userGroups.stream()
                .map(userGroup -> convertToUserGroupQueryModel(userGroup))
                .collect(Collectors.toList());

        return userGroupQueryModels;
    }

    public UserGroupQueryModel convertToUserGroupQueryModel(Map<String, Object> userGroup) {
        return new UserGroupQueryModel((String)userGroup.get("group_id"),
                (String)userGroup.get("created_by"),
                (String)userGroup.get("group_name"));
    }

    @Override
    public int selectUserGroupCountByUserId(String userId) {
        Integer count = jdbc.queryForObject(
                "SELECT COUNT(*) FROM user_group WHERE group_id IN (SELECT group_id FROM group_user_belonging WHERE user_id = ?)",
                Integer.class, userId);

        return count;
    }

    @Override
    public UserGroupQueryModel selectUserGroupByGroupId(String userGroupId) throws UserGroupException{
        try {
            Map<String, Object> userGroupData = jdbc.queryForMap("SELECT * FROM user_group WHERE group_id = ?", userGroupId);

            UserGroupQueryModel userGroupQueryModel = convertToUserGroupQueryModel(userGroupData);

            return userGroupQueryModel;
        } catch (EmptyResultDataAccessException e) {
            throw new UserGroupException("This userGroupId doesn't exist.", e);
        }
    }

}
