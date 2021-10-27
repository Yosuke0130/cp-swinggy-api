package com.example.demo.infrastructure.usergroupmember;

import com.example.demo.application.usergroupmember.UserGroupMemberException;
import com.example.demo.domain.usergroupmember.UserGroupMember;
import com.example.demo.domain.usergroupmember.UserGroupMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class JdbcUserGroupMemberRepository implements UserGroupMemberRepository {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public void insertUserGroupMember(UserGroupMember userGroupMember) throws DataAccessException {
        try {
            if(!userGroupMember.getIsOwner()) {
                jdbc.update("INSERT INTO user_group_member(user_group_member_id, group_id, user_id) VALUES(?, ?, ?)",
                        userGroupMember.getUserGroupMemberId(),
                        userGroupMember.getUserGroupId(),
                        userGroupMember.getUserId());
            } else {
                jdbc.update("INSERT INTO user_group_member(user_group_member_id, group_id, user_id, is_owner) VALUES(?, ?, ?, ?)",
                        userGroupMember.getUserGroupMemberId(),
                        userGroupMember.getUserGroupId(),
                        userGroupMember.getUserId(),
                        true);
            }
        } catch (DataAccessException e) {
            throw new UserGroupMemberException("DB access error occurred when registering new user_group_member.", e);
        }
    }

}
