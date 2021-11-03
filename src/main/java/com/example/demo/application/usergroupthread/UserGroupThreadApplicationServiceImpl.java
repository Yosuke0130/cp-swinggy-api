package com.example.demo.application.usergroupthread;

import com.example.demo.application.usergroup.UserGroupQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserGroupThreadApplicationServiceImpl implements UserGroupThreadApplicationService{

    @Autowired
    UserGroupQueryService userGroupQueryService;

    @Autowired
    UserGroupThreadQueryService userGroupThreadQueryService;

    private static final int USER_GROUP_THREAD_DEFAULT_PAGE = 0;
    private static final int USER_GROUP_THREAD_DEFAULT_PER = 100;

    @Override
    public UserGroupThreadListQueryModel getUserGroupThreads(String userGroupId, Optional<Integer> page, Optional<Integer> per) throws IllegalArgumentException {

        if(!userGroupQueryService.exists(userGroupId)) {
            throw new IllegalArgumentException("This userGroup doesn't exist.");
        }

        int pageValue = page.orElse(USER_GROUP_THREAD_DEFAULT_PAGE);
        int perValue = per.orElse(USER_GROUP_THREAD_DEFAULT_PER);

        UserGroupThreadListQueryModel userGroupThreadListQueryModel = userGroupThreadQueryService.selectList(userGroupId, pageValue, perValue);

        return userGroupThreadListQueryModel;
    }
}
