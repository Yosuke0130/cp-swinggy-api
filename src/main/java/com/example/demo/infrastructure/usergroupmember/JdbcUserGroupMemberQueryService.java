package com.example.demo.infrastructure.usergroupmember;

import com.example.demo.application.usergroupmember.UserGroupMemberException;
import com.example.demo.application.usergroupmember.UserGroupMemberListQueryModel;
import com.example.demo.application.usergroupmember.UserGroupMemberQueryModel;
import com.example.demo.application.usergroupmember.UserGroupMemberQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class JdbcUserGroupMemberQueryService implements UserGroupMemberQueryService {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    @Transactional
    public UserGroupMemberListQueryModel selectUserGroupMembersByUserGroupId(String userGroupId, int page, int per) {
        int offset = 0;
        if(page > 0) {offset = page * per;}

        List<Map<String, Object>> members = jdbc.queryForList(
                "SELECT * FROM user_group_member WHERE group_id = ? ORDER BY created_at DESC LIMIT ? OFFSET ?",
                userGroupId, per, offset);

        List<UserGroupMemberQueryModel> memberQueryModelList = members.stream()
                .map(member -> convertToUserGroupMemberQueryModel(member))
                .collect(Collectors.toList());

        Integer count = jdbc.queryForObject(
                "SELECT COUNT(*) FROM user_group_member WHERE group_id = ?",
                Integer.class, userGroupId);

        UserGroupMemberListQueryModel userGroupMemberListQueryModel = new UserGroupMemberListQueryModel(memberQueryModelList, count);

        return userGroupMemberListQueryModel;
    }

    private UserGroupMemberQueryModel convertToUserGroupMemberQueryModel(Map<String, Object> member) {
        return new UserGroupMemberQueryModel(
                (String) member.get("user_group_member_id"),
                (String) member.get("group_id"),
                (String) member.get("user_id"),
                (boolean) member.get("is_owner"));
    }

    @Override
    public UserGroupMemberQueryModel selectUserGroupMember(String groupId, String userId) throws UserGroupMemberException{
        try {
            Map<String, Object> userGroupMember = jdbc.queryForMap("SELECT * FROM user_group_member WHERE group_id = ? AND user_id = ?", groupId, userId);

            UserGroupMemberQueryModel userGroupMemberQueryModel = convertToUserGroupMemberQueryModel(userGroupMember);

            return userGroupMemberQueryModel;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        } catch (DataAccessException e) {
            throw new UserGroupMemberException("query failed.");
        }
    }

    @Override
    public UserGroupMemberQueryModel selectUserGroupMemberByMemberId(String userGroupMemberId) throws IllegalArgumentException, UserGroupMemberException{
        try {
            Map<String, Object> member = jdbc.queryForMap("SELECT * FROM user_group_member WHERE user_group_member_id = ?", userGroupMemberId);

            return convertToUserGroupMemberQueryModel(member);
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new IllegalArgumentException("This userGroupMemberId is invalid.", e);
        } catch (DataAccessException e) {
            throw new UserGroupMemberException("Query failed.");
        }
    }

    @Override
    public boolean exists(String memberId) throws UserGroupMemberException {
        try {
            Integer count = jdbc.queryForObject("SELECT COUNT(*) FROM user_group_member WHERE user_group_member_id = ?", Integer.class, memberId);

            return count > 0;

        } catch (IncorrectResultSizeDataAccessException e) {
            return false;
        } catch (DataAccessException e) {
            throw new UserGroupMemberException("query fails.");
        }
    }

}
