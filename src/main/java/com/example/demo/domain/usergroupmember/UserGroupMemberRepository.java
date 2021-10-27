package com.example.demo.domain.usergroupmember;

import org.springframework.dao.DataAccessException;

public interface UserGroupMemberRepository {

    //memo: 下記２つは１つにまとめた方がクライアントが間違えずらい。分岐の条件が呼び出し元に依存する場合どう実装する？
    public void insertUserGroupMember(UserGroupMember userGroupMember) throws DataAccessException;

    public void insertUserGroupMemberByOwner(UserGroupMember userGroupMember) throws DataAccessException;


}
