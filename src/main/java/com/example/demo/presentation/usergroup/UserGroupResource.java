package com.example.demo.presentation.usergroup;

import com.example.demo.application.usergroup.UserGroupDTO;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserGroupResource {

    private String groupId;
    private String createdBy;
    private String groupName;

    //ユースケースごとにDTOが返却されるとしたら、このクラス不要？
    //FEの求める形（命名など）に変換するから必要？
    public UserGroupResource(UserGroupDTO userGroup) {
        this.groupId = userGroup.getUserGroupId();
        this.createdBy = userGroup.getCreatedBy();
        this.groupName = userGroup.getUserGroupName();
    }

    public String getGroupId() {return this.groupId;}
    public String getCreatedBy() {return this.createdBy;}
    public String getGroupName() {return this.groupName;}

}
