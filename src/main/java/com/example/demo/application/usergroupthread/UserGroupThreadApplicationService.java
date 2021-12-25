package com.example.demo.application.usergroupthread;

import java.util.Optional;

public interface UserGroupThreadApplicationService {

    public UserGroupThreadListQueryModel getUserGroupThreads(String userGroupId, Optional<Integer> page, Optional<Integer> per) throws IllegalArgumentException;

    public UserGroupThreadQueryModel getUserGroupThreadById(String userGroupId, String userGroupThreadId) throws IllegalArgumentException, UserGroupThreadException;

    public void createThread(String userGroupId, String name) throws IllegalStateException, IllegalArgumentException, UserGroupThreadException;

    public void updateThread(String userGroupId, String id, String name) throws IllegalStateException, IllegalArgumentException, UserGroupThreadException;

    public void deleteThread(String userGroupId, String id) throws IllegalStateException, IllegalArgumentException, UserGroupThreadException;

    public UserGroupCommentListQueryModel getUserGroupComments(String id, String userGroupId, Optional<Integer> page, Optional<Integer> per) throws IllegalStateException;

    public void createGroupComment(String userGroupId, String threadId, String memberId, String text) throws IllegalStateException, UserGroupThreadException, IllegalArgumentException;
}
