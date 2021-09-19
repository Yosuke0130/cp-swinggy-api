package com.example.demo.application.usergroup;

import com.example.demo.Logging;
import com.example.demo.domain.usergroup.UserGroup;
import com.example.demo.domain.usergroup.UserGroupRepository;
import com.example.demo.domain.usergroup.UserGroupService;
import com.example.demo.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserGroupApplicationServiceImpl implements UserGroupApplicationService {

    @Autowired
    Logging logger;

    @Autowired
    UserGroupService userGroupService;

    @Autowired
    UserGroupRepository userGroupRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserGroupQueryService userGroupQueryService;

    @Override
    public void createUserGroup(String userGroupName, String createdBy) throws IllegalStateException, IllegalArgumentException, UserGroupException {

        if(!userRepository.exists(createdBy)) {
            throw new IllegalArgumentException("This created_by doesn't exist.");
        }

        UserGroup userGroup = new UserGroup(userGroupName, createdBy);

        userGroupRepository.insertUserGroup(userGroup);
    }

    private static final int USER_GROUP_DEFAULT_PAGE = 0;
    private static final int USER_GROUP_DEFAULT_PER = 100;
    @Override
    public List<UserGroupQueryModel> getBelongedUserGroups(String userId, Optional<Integer> page, Optional<Integer> per) throws IllegalArgumentException{

        int pageValue = page.orElse(USER_GROUP_DEFAULT_PAGE);
        int perValue = per.orElse(USER_GROUP_DEFAULT_PER);

        if(!userRepository.exists(userId)) {
            throw new IllegalArgumentException("This userId doesn't exist.");
        }
        List<UserGroupQueryModel> userGroups = userGroupQueryService.selectUserGroupByUserId(userId, pageValue, perValue);

        return userGroups;
    }

    @Override
    public int getBelongedUserGroupCount(String userId) {

        int total = userGroupQueryService.selectUserGroupCountByUserId(userId);

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
                userGroupQueryModel.getCreatedBy());

        userGroup.changeUserGroupName(userGroupName);

        userGroupRepository.updateUserGroupName(userGroup);
        logger.info(userGroup.getUserGroupId() + ": userGroupName has changed to " + userGroupName);
    }

    @Override
    public void deleteUserGroup(String userGroupId) throws UserGroupException, IllegalArgumentException{

        UserGroupQueryModel userGroupQueryModel = userGroupQueryService.selectUserGroupByGroupId(userGroupId);

        UserGroup userGroup = new UserGroup(userGroupQueryModel.getUserGroupId(),
                userGroupQueryModel.getUserGroupName(),
                userGroupQueryModel.getCreatedBy());

        userGroupRepository.deleteUserGroup(userGroup);
    }

}
