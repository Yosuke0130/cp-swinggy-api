package com.example.demo.infrastructure.group;

import com.example.demo.application.group.GroupException;
import com.example.demo.application.wish_date.WishDateException;
import com.example.demo.domain.group.Group;
import com.example.demo.domain.group.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class jdbcGroupRepository implements GroupRepository {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public int selectGroupByGroupName(String groupName) {

        Integer count = jdbc.queryForObject("SELECT COUNT(*) FROM user_group WHERE group_name = ?", Integer.class, groupName);

        return count;
    }

    @Override
    public void insertGroup(Group group) {
        try {
            jdbc.update("INSERT INTO user_group(group_id, group_name, created_by) VALUES(?, ?, ?)",
                    group.getGroupId(),
                    group.getGroupName(),
                    group.getCreatedBy());

        } catch (DataAccessException e) {
            throw new GroupException("DB access error occurred when registering new group.", e);
        }

    }
}
