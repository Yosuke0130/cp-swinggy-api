package com.example.demo.domain.usergroupmember;

import com.example.demo.application.usergroupmember.UserGroupMemberException;
import com.example.demo.application.usergroupmember.UserGroupMemberQueryModel;
import com.example.demo.application.usergroupmember.UserGroupMemberQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserGroupMemberService {

    @Autowired
    UserGroupMemberQueryService userGroupMemberQueryService;

    public boolean isMember(UserGroupMember userGroupMember) throws UserGroupMemberException {

        UserGroupMemberQueryModel userGroupMemberQueryModel =
                userGroupMemberQueryService.selectUserGroupMember(userGroupMember.getUserGroupId(), userGroupMember.getUserId());

        if(userGroupMemberQueryModel == null) {
            return false;
        }

        return true;
    }

}
