package com.example.demo.application.usergroupthread;

public interface UserGroupThreadQueryService {

    public UserGroupThreadListQueryModel selectList(String userGroupThreadId, int page, int per);
}
