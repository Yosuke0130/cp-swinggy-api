package com.example.demo.application.usergroupmember;


import java.util.List;

public class UserGroupMemberListQueryModel {

    private List<UserGroupMemberQueryModel> userGroupMemberListQueryModel;
    private int total;

    public UserGroupMemberListQueryModel(List<UserGroupMemberQueryModel> userGroupMemberListQueryModel, int total) {
        this.userGroupMemberListQueryModel = userGroupMemberListQueryModel;
        this.total = total;
    }

    public List<UserGroupMemberQueryModel> getUserGroupMemberListQueryModel() {return this.userGroupMemberListQueryModel;}
    public int getTotal() {return this.total;}

}
