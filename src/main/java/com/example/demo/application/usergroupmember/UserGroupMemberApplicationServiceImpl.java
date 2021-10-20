package com.example.demo.application.usergroupmember;

import com.example.demo.application.usergroup.UserGroupQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserGroupMemberApplicationServiceImpl implements UserGroupMemberApplicationService{

    @Autowired
    UserGroupMemberQueryService userGroupMemberQueryService;

    @Autowired
    UserGroupQueryService userGroupQueryService;

    private static final int USER_GROUP_MEMBER_DEFAULT_PAGE = 0;
    private static final int USER_GROUP_MEMBER_DEFAULT_PER = 100;

    @Override
    public List<UserGroupMemberQueryModel> getUserGroupMembers(String userGroupId, Optional<Integer> page, Optional<Integer> per)
            throws IllegalArgumentException {

        if(!userGroupQueryService.exists(userGroupId)) {
            throw new IllegalArgumentException("This userGroup doesn't exist.");
        }

        int pageValue = page.orElse(USER_GROUP_MEMBER_DEFAULT_PAGE);
        int perValue = per.orElse(USER_GROUP_MEMBER_DEFAULT_PER);

        List<UserGroupMemberQueryModel> members = userGroupMemberQueryService.selectUserGroupMembersByUserGroupId(userGroupId, pageValue, perValue);

        return members;
    }

    @Override
    public int getUserGroupMemberCount(String userGroupId) {
        int count = userGroupMemberQueryService.selectUserGroupMemberCount(userGroupId);
        return count;
    }
}
