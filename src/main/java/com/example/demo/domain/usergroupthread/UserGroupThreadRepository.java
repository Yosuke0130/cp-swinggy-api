package com.example.demo.domain.usergroupthread;

import com.example.demo.application.usergroupthread.UserGroupThreadException;

public interface UserGroupThreadRepository {

    public void insert(UserGroupThread thread) throws UserGroupThreadException;
}
