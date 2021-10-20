package com.example.demo.infrastructure.usergroupmember;

import com.example.demo.application.usergroupmember.UserGroupMemberQueryModel;
import com.example.demo.application.usergroupmember.UserGroupMemberQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class jdbcUserGroupMemberQueryService implements UserGroupMemberQueryService {

    @Autowired
    JdbcTemplate jdbc;

    public List<UserGroupMemberQueryModel> selectUserGroupMembersByUserGroupId(String userGroupId, int page, int per) {
        int offset = 0;
        if(page > 0) {offset = page * per;}

        List<Map<String, Object>> members = jdbc.queryForList(
                "SELECT * FROM user_group_member WHERE group_id = ? ORDER BY created_at DESC LIMIT ? OFFSET ?",
                userGroupId, per, offset);

        List<UserGroupMemberQueryModel> memberQueryModelList = members.stream()
                .map(member -> convertToUserGroupMemberQueryModel(member))
                .collect(Collectors.toList());

        return memberQueryModelList;

    }

    private UserGroupMemberQueryModel convertToUserGroupMemberQueryModel(Map<String, Object> member) {
        return new UserGroupMemberQueryModel(
                (String) member.get("user_group_member_id"),
                (String) member.get("group_id"),
                (String) member.get("user_id"));
    }

    public int selectUserGroupMemberCount(String groupId) {
        Integer count = jdbc.queryForObject(
                "SELECT COUNT(*) FROM user_group_member WHERE group_id = ?",
                Integer.class, groupId);
        return count;
    }
}
