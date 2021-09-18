package com.example.demo.infrastructure.usergroup;

import com.example.demo.application.usergroup.UserGroupException;
import com.example.demo.application.usergroup.UserGroupDTO;
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
    public List<UserGroupDTO> selectUserGroupByCreatedBy(String createdBy, int page, int per) {
        int offset = 0;
        if(page > 0) {offset = page * per;}

        List<Map<String, Object>> userGroups = jdbc.queryForList("SELECT * FROM user_group WHERE created_by = ? ORDER BY created_at DESC LIMIT ? OFFSET ?",
                createdBy, per, offset);

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
    public int selectUserGroupCountById(String createdBy) {
        Integer count = jdbc.queryForObject("SELECT COUNT(*) FROM user_group WHERE created_by = ?", Integer.class, createdBy);

        return count;
    }

    @Override
    public UserGroupDTO selectUserGroupById(String userGroupId) {
        try {
            Map<String, Object> userGroupData = jdbc.queryForMap("SELECT * FROM user_group WHERE group_id = ?", userGroupId);

            UserGroupDTO userGroupDTO = convertToUserGroupDTO(userGroupData);

            return userGroupDTO;
        } catch (EmptyResultDataAccessException e) {
            throw new UserGroupException("This userGroupId doesn't exist.", e);
        }
    }

}
