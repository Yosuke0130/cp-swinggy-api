package com.example.demo.application.usergroupthread;

import com.example.demo.Logging;
import com.example.demo.application.usergroup.UserGroupQueryService;
import com.example.demo.domain.usergroupthread.UserGroupThread;
import com.example.demo.domain.usergroupthread.UserGroupThreadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserGroupThreadApplicationServiceImpl implements UserGroupThreadApplicationService{

    @Autowired
    private UserGroupQueryService userGroupQueryService;

    @Autowired
    private UserGroupThreadQueryService userGroupThreadQueryService;

    @Autowired
    private UserGroupThreadRepository userGroupThreadRepository;

    @Autowired
    private Logging logger;

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

    @Override
    public UserGroupThreadQueryModel getUserGroupThreadById(String userGroupId, String userGroupThreadId) throws IllegalArgumentException, UserGroupThreadException{

        if (!userGroupThreadQueryService.exists(userGroupId, userGroupThreadId)) {
            throw new IllegalArgumentException("This userGroupThread doesn't exist.");
        }

        UserGroupThreadQueryModel thread = userGroupThreadQueryService.selectThreadById(userGroupThreadId);

        return thread;
    }

    @Override
    public void createThread(String userGroupId, String name) throws IllegalStateException, IllegalArgumentException, UserGroupThreadException {
        if(!userGroupQueryService.exists(userGroupId)) {
            throw new IllegalStateException("groupId doesn't exists.");
        }

        UserGroupThread thread = new UserGroupThread(userGroupId, name);

        userGroupThreadRepository.insert(thread);
        logger.info("userGroupThread created: " + thread.getId());
    }
}
