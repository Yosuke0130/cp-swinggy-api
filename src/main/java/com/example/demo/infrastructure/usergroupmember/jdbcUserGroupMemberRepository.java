package com.example.demo.infrastructure.usergroupmember;

import com.example.demo.domain.usergroupmember.UserGroupMember;
import com.example.demo.domain.usergroupmember.UserGroupMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class jdbcUserGroupMemberRepository implements UserGroupMemberRepository {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public void insertUserGroupMember(UserGroupMember userGroupMember) {

        jdbc.update("INSERT INTO user_group_member(user_group_member_id, group_id, user_id) VALUES(?, ?, ?)",
                userGroupMember.getUserGroupMemberId(),
                userGroupMember.getUserGroupId(),
                userGroupMember.getUserId());
    }
}
