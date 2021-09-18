package com.example.demo.application.usergroup;

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
    public List<UserGroupDTO> getOwnedUserGroups(String createdBy, Optional<Integer> page, Optional<Integer> per) throws IllegalArgumentException{

        int pageValue = page.orElse(USER_GROUP_DEFAULT_PAGE);
        int perValue = per.orElse(USER_GROUP_DEFAULT_PER);

        if(!userRepository.exists(createdBy)) {
            throw new IllegalArgumentException("This userId doesn't exist.");
        }
        List<UserGroupDTO> userGroups = userGroupQueryService.selectUserGroupByCreatedBy(createdBy, pageValue, perValue);

        return userGroups;
    }

    @Override
    public int getOwnedUserGroupCount(String createdBy) {

        int total = userGroupQueryService.selectUserGroupCountById(createdBy);

        return total;
    }

    @Override
    public UserGroupDTO getUserGroup(String userGroupId) throws UserGroupException{

        UserGroupDTO userGroupDTO = userGroupQueryService.selectUserGroupById(userGroupId);

        return userGroupDTO;
    }

}
