package com.example.demo.application.user_group;

import com.example.demo.domain.user_group.UserGroup;
import com.example.demo.domain.user_group.UserGroupRepository;
import com.example.demo.domain.user_group.UserGroupService;
import com.example.demo.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserGroupApplicationServiceImpl implements UserGroupApplicationService {

    @Autowired
    UserGroupService userGroupService;

    @Autowired
    UserGroupRepository userGroupRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public void createUserGroup(String userGroupName, String createdBy) throws IllegalStateException, IllegalArgumentException, GroupException {

        // check with userService if this user exists
        if(!userRepository.exists(createdBy)) {
            throw new IllegalArgumentException("This created_by doesn't exist.");
        }

        // create userGroup
        UserGroup userGroup = new UserGroup(userGroupName, createdBy);

        // calls repository to insert data
        userGroupRepository.insertUserGroup(userGroup);
    }

}
