package com.example.demo.application.usergroup;

import com.example.demo.domain.usergroup.UserGroup;
import com.example.demo.domain.usergroup.UserGroupRepository;
import com.example.demo.domain.usergroup.UserGroupService;
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

        if(!userRepository.exists(createdBy)) {
            throw new IllegalArgumentException("This created_by doesn't exist.");
        }

        UserGroup userGroup = new UserGroup(userGroupName, createdBy);

        userGroupRepository.insertUserGroup(userGroup);
    }

}
