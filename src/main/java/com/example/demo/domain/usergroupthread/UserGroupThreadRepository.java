package com.example.demo.domain.usergroupthread;

import com.example.demo.application.usergroupthread.UserGroupThreadException;

public interface UserGroupThreadRepository {

    public void insert(UserGroupThread thread) throws UserGroupThreadException;

    public void updateName(UserGroupThread thread) throws UserGroupThreadException;

    public void delete(UserGroupThread thread) throws UserGroupThreadException;
}
