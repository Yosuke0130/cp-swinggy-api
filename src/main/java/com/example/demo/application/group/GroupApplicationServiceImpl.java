package com.example.demo.application.group;

import com.example.demo.domain.group.Group;
import com.example.demo.domain.group.GroupRepository;
import com.example.demo.domain.group.GroupService;
import com.example.demo.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupApplicationServiceImpl implements GroupApplicationService {

    @Autowired
    GroupService groupService;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public void createGroup(String groupName, String createdBy) throws IllegalStateException, IllegalArgumentException, GroupException {

        // check with userService if this user exists
        if(!userRepository.exists(createdBy)) {
            throw new IllegalArgumentException("This created_by doesn't exist on user table.");
        }

        // create group
        Group group = new Group(groupName, createdBy);

        // calls repository to insert data
        groupRepository.insertGroup(group);
    }

}
