package com.example.demo.domain.usergroupthread;

import com.example.demo.application.usergroupthread.UserGroupThreadQueryModel;
import com.example.demo.application.usergroupthread.UserGroupThreadQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserGroupThreadService {

    @Autowired
    UserGroupThreadQueryService userGroupThreadQueryService;

    public boolean validateName(String userGroupId, String name) {
        UserGroupThreadQueryModel queryModel = userGroupThreadQueryService.selectThreadByNameInGroup(name, userGroupId);
        return queryModel == null;
    }
}
