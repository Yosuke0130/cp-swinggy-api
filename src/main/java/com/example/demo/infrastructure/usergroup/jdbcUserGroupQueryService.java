package com.example.demo.infrastructure.usergroup;

import com.example.demo.application.usergroup.UserGroupDTO;
import com.example.demo.application.usergroup.UserGroupQueryService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<UserGroupDTO> selectUserGroupById(String userId, int page, int per) {
        int offset = 0;
        if(page > 0) {offset = page * per;}

        List<Map<String, Object>> userGroups = jdbc.queryForList("SELECT * FROM user_group WHERE created_by = ? ORDER BY created_at DESC LIMIT ? OFFSET ?",
                userId, per, offset);

        List<UserGroupDTO> userGroupDTOList = userGroups.stream()
                .map(userGroup -> convertToUserGroupDTO(userGroup))
                .collect(Collectors.toList());

        return userGroupDTOList;
    }

    public UserGroupDTO convertToUserGroupDTO(Map<String, Object> userGroup) {
        return new UserGroupDTO((String)userGroup.get("group_id"),
                (String)userGroup.get("created_by"),
                (String)userGroup.get("group_name"));
    }

    @Override
    public int selectUserGroupCountById(String userId) {
        Integer count = jdbc.queryForObject("SELECT COUNT(*) FROM user_group WHERE created_by = ?", Integer.class, userId);

        return count;
    }

}
