package com.example.demo.application.usergroupthread;

public interface UserGroupThreadQueryService {

    public UserGroupThreadListQueryModel selectList(String userGroupThreadId, int page, int per);

    public boolean exists(String userGroupId, String userGroupThreadId) throws UserGroupThreadException;

    public UserGroupThreadQueryModel selectThreadById(String userGroupThreadId);

}
