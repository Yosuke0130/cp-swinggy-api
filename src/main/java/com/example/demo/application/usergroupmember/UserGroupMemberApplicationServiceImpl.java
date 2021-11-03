package com.example.demo.application.usergroupmember;

import com.example.demo.Logging;
import com.example.demo.application.usergroup.UserGroupQueryService;
import com.example.demo.domain.user.UserRepository;
import com.example.demo.domain.usergroupmember.UserGroupMember;
import com.example.demo.domain.usergroupmember.UserGroupMemberRepository;
import com.example.demo.domain.usergroupmember.UserGroupMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserGroupMemberApplicationServiceImpl implements UserGroupMemberApplicationService{

    @Autowired
    UserGroupMemberQueryService userGroupMemberQueryService;

    @Autowired
    UserGroupQueryService userGroupQueryService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserGroupMemberRepository userGroupMemberRepository;

    @Autowired
    UserGroupMemberService userGroupMemberService;

    @Autowired
    Logging logger;

    private static final int USER_GROUP_MEMBER_DEFAULT_PAGE = 0;
    private static final int USER_GROUP_MEMBER_DEFAULT_PER = 100;

    @Override
    public UserGroupMemberListQueryModel getUserGroupMembers(String userGroupId, Optional<Integer> page, Optional<Integer> per)
            throws IllegalArgumentException {

        if(!userGroupQueryService.exists(userGroupId)) {
            throw new IllegalArgumentException("This userGroup doesn't exist.");
        }

        int pageValue = page.orElse(USER_GROUP_MEMBER_DEFAULT_PAGE);
        int perValue = per.orElse(USER_GROUP_MEMBER_DEFAULT_PER);

        UserGroupMemberListQueryModel userGroupMemberListQueryModel = userGroupMemberQueryService.selectUserGroupMembersByUserGroupId(userGroupId, pageValue, perValue);

        return userGroupMemberListQueryModel;
    }

    @Override
    public void registerUserGroupMember(String userGroupId, String userId) throws UserGroupMemberException, IllegalArgumentException {
        if(!userGroupQueryService.exists(userGroupId)) {
            throw new IllegalArgumentException("This groupId doesn't exist.");
        }

        if(!userRepository.exists(userId)) {
            throw new IllegalArgumentException("This userId doesn't exist.");
        }

        UserGroupMember userGroupMember = new UserGroupMember(userGroupId, userId, false);

        if(userGroupMemberService.isMember(userGroupMember)) {
            throw new IllegalArgumentException("This user has already belonged to this group.");
        }

        userGroupMemberRepository.insertUserGroupMember(userGroupMember);
    }

    @Override
    public void deleteUserGroupMember(String userGroupMemberId) throws UserGroupMemberException, IllegalArgumentException {

        UserGroupMemberQueryModel userGroupMemberQueryModel = userGroupMemberQueryService.selectUserGroupMemberByMemberId(userGroupMemberId);

        UserGroupMember userGroupMember = new UserGroupMember(
                userGroupMemberQueryModel.getUserGroupMemberId(),
                userGroupMemberQueryModel.getUserGroupId(),
                userGroupMemberQueryModel.getUserId(),
                userGroupMemberQueryModel.getIsOwner());

        if(!userGroupMember.getIsOwner()) {
            logger.info("call repository to delete member: " + userGroupMember.getUserGroupMemberId());
            userGroupMemberRepository.deleteUserGroupMember(userGroupMember);
        } else {
            throw new IllegalArgumentException("Owner cannot delete group member.");
        }
    }

}
