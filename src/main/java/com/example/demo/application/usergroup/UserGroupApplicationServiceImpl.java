package com.example.demo.application.usergroup;

import com.example.demo.Logging;
import com.example.demo.domain.usergroup.UserGroup;
import com.example.demo.domain.usergroup.UserGroupRepository;
import com.example.demo.domain.user.UserRepository;
import com.example.demo.domain.usergroupmember.UserGroupMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserGroupApplicationServiceImpl implements UserGroupApplicationService {

    @Autowired
    Logging logger;

    @Autowired
    UserGroupRepository userGroupRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserGroupQueryService userGroupQueryService;

    @Override
    public void createUserGroup(String userGroupName, String owner) throws IllegalStateException, IllegalArgumentException, UserGroupException {

        if(!userRepository.exists(owner)) {
            throw new IllegalArgumentException("This owner doesn't exist.");
        }

        UserGroup userGroup = new UserGroup(userGroupName, owner);

        UserGroupMember userGroupMember = new UserGroupMember(userGroup.getUserGroupId(), userGroup.getOwner());

        userGroupRepository.insert(userGroup, userGroupMember);
    }

    private static final int USER_GROUP_DEFAULT_PAGE = 0;
    private static final int USER_GROUP_DEFAULT_PER = 100;
    @Override
    public List<UserGroupQueryModel> getBelongedUserGroups(String member, Optional<Integer> page, Optional<Integer> per) throws IllegalArgumentException{

        int pageValue = page.orElse(USER_GROUP_DEFAULT_PAGE);
        int perValue = per.orElse(USER_GROUP_DEFAULT_PER);

        if(!userRepository.exists(member)) {
            throw new IllegalArgumentException("This member doesn't exist.");
        }
        List<UserGroupQueryModel> userGroups = userGroupQueryService.selectUserGroupByMemberId(member, pageValue, perValue);

        return userGroups;
    }

    @Override
    public int getBelongedUserGroupCount(String member) {

        int total = userGroupQueryService.selectUserGroupCountByMemberId(member);

        return total;
    }

    @Override
    public UserGroupQueryModel getUserGroup(String userGroupId) throws UserGroupException{

        UserGroupQueryModel userGroupQueryModel = userGroupQueryService.selectUserGroupByGroupId(userGroupId);

        return userGroupQueryModel;
    }

    @Override
    public void changeUserGroupName(String userGroupId, String userGroupName) throws UserGroupException, IllegalArgumentException{

        UserGroupQueryModel userGroupQueryModel = userGroupQueryService.selectUserGroupByGroupId(userGroupId);

        UserGroup userGroup = new UserGroup(userGroupQueryModel.getUserGroupId(),
                userGroupQueryModel.getUserGroupName(),
                userGroupQueryModel.getOwner());

        userGroup.changeUserGroupName(userGroupName);

        userGroupRepository.update(userGroup);
        logger.info(userGroup.getUserGroupId() + ": userGroupName has changed to " + userGroupName);
    }

    @Override
    public void deleteUserGroup(String userGroupId) throws UserGroupException, IllegalArgumentException{

        UserGroupQueryModel userGroupQueryModel = userGroupQueryService.selectUserGroupByGroupId(userGroupId);

        UserGroup userGroup = new UserGroup(userGroupQueryModel.getUserGroupId(),
                userGroupQueryModel.getUserGroupName(),
                userGroupQueryModel.getOwner());

        userGroupRepository.delete(userGroup);
    }

}
