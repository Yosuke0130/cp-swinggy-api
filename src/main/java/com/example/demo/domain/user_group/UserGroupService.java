package com.example.demo.domain.user_group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserGroupService {

    @Autowired
    UserGroupRepository userGroupRepository;

}
