package com.example.demo.application.usergroupmember;


public interface UserGroupMemberQueryService {

    public UserGroupMemberListQueryModel selectUserGroupMembersByUserGroupId(String userGroupId, int page, int per);

    public UserGroupMemberQueryModel selectUserGroupMember(String groupId, String userId) throws UserGroupMemberException;

    public UserGroupMemberQueryModel selectUserGroupMemberByMemberId(String userGroupMemberId) throws IllegalArgumentException, UserGroupMemberException;

    public boolean exists(String memberId) throws UserGroupMemberException;

}
