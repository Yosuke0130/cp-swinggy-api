package com.example.demo.application.usergroupthread;

import com.example.demo.Logging;
import com.example.demo.application.usergroup.UserGroupQueryService;
import com.example.demo.application.usergroupmember.UserGroupMemberException;
import com.example.demo.application.usergroupmember.UserGroupMemberQueryService;
import com.example.demo.domain.usergroupthread.UserGroupComment;
import com.example.demo.domain.usergroupthread.UserGroupThread;
import com.example.demo.domain.usergroupthread.UserGroupThreadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserGroupThreadApplicationServiceImpl implements UserGroupThreadApplicationService {

    @Autowired
    private UserGroupQueryService userGroupQueryService;

    @Autowired
    private UserGroupThreadQueryService userGroupThreadQueryService;

    @Autowired
    private UserGroupThreadRepository userGroupThreadRepository;

    @Autowired
    private Logging logger;

    @Autowired
    private UserGroupMemberQueryService userGroupMemberQueryService;

    private static final int USER_GROUP_THREAD_DEFAULT_PAGE = 0;
    private static final int USER_GROUP_THREAD_DEFAULT_PER = 100;

    @Override
    public UserGroupThreadListQueryModel getUserGroupThreads(String userGroupId, Optional<Integer> page, Optional<Integer> per) throws IllegalArgumentException {

        if (!userGroupQueryService.exists(userGroupId)) {
            throw new IllegalArgumentException("This userGroup doesn't exist.");
        }

        int pageValue = page.orElse(USER_GROUP_THREAD_DEFAULT_PAGE);
        int perValue = per.orElse(USER_GROUP_THREAD_DEFAULT_PER);

        UserGroupThreadListQueryModel userGroupThreadListQueryModel = userGroupThreadQueryService.selectList(userGroupId, pageValue, perValue);

        return userGroupThreadListQueryModel;
    }

    @Override
    public UserGroupThreadQueryModel getUserGroupThreadById(String userGroupId, String userGroupThreadId) throws IllegalArgumentException, UserGroupThreadException {

        if (!userGroupThreadQueryService.exists(userGroupId, userGroupThreadId)) {
            throw new IllegalArgumentException("This userGroupThread doesn't exist.");
        }

        UserGroupThreadQueryModel thread = userGroupThreadQueryService.selectThreadById(userGroupThreadId);

        return thread;
    }

    @Override
    public void createThread(String userGroupId, String name) throws IllegalStateException, IllegalArgumentException, UserGroupThreadException {
        if (!userGroupQueryService.exists(userGroupId)) {
            throw new IllegalStateException("groupId doesn't exists.");
        }

        UserGroupThread thread = new UserGroupThread(userGroupId, name);

        userGroupThreadRepository.insert(thread);
        logger.info("userGroupThread created: " + thread.getId());
    }

    @Override
    public void updateThread(String userGroupId, String id, String name) throws IllegalStateException, IllegalArgumentException, UserGroupThreadException {
        if (!userGroupThreadQueryService.exists(userGroupId, id)) {
            throw new IllegalStateException("This userGroupThread doesn't exist.");
        }

        UserGroupThread thread = new UserGroupThread(userGroupId, id, name);

        userGroupThreadRepository.update(thread);
        logger.info("Thread has been updated :" + thread.getId());
    }

    @Override
    public void deleteThread(String userGroupId, String id) throws IllegalStateException, IllegalArgumentException, UserGroupThreadException {
        if (!userGroupThreadQueryService.exists(userGroupId, id)) {
            throw new IllegalStateException("This userGroupThread doesn't exist.");
        }

        UserGroupThreadQueryModel threadQueryModel = userGroupThreadQueryService.selectThreadById(id);

        UserGroupThread thread = new UserGroupThread(
                threadQueryModel.getUserGroupId(),
                threadQueryModel.getUserGroupThreadId(),
                threadQueryModel.getName());

        userGroupThreadRepository.delete(thread);
        logger.info("Thread has been deleted :" + thread.getId());
    }

    private static final int USER_GROUP_COMMENT_DEFAULT_PAGE = 0;
    private static final int USER_GROUP_COMMENT_DEFAULT_PER = 100;

    @Override
    public UserGroupCommentListQueryModel getUserGroupComments(String threadId, String userGroupId, Optional<Integer> page, Optional<Integer> per) throws IllegalStateException {
        if (!userGroupThreadQueryService.exists(userGroupId, threadId)) {
            throw new IllegalStateException("This userGroupThread doesn't exist.");
        }

        int pageValue = page.orElse(USER_GROUP_COMMENT_DEFAULT_PAGE);
        int perValue = per.orElse(USER_GROUP_COMMENT_DEFAULT_PER);

        return userGroupThreadQueryService.selectCommentList(threadId, pageValue, perValue);
    }

    @Override
    public void createGroupComment(String userGroupId, String threadId, String memberId, String text)
            throws IllegalStateException, UserGroupThreadException, IllegalArgumentException
    {
        try {
            if(!userGroupThreadQueryService.exists(userGroupId, threadId)) {
                throw new IllegalStateException("This userGroupThread doesn't exist.");
            }

            if(!userGroupMemberQueryService.exists(memberId)) {
                throw new IllegalArgumentException("This groupMember doesn't exist.");
            }

            UserGroupComment groupComment = new UserGroupComment(threadId, memberId, text);

            userGroupThreadRepository.insertComment(groupComment);
        } catch (UserGroupMemberException e) {
            throw new UserGroupThreadException(e.getMessage(), e);
        }
    }

}
