package com.example.demo.domain.usergroupmember;

import com.example.demo.Logging;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class UserGroupMemberId {

    @Autowired
    Logging logger;

    private String userGroupMemberId;

    public UserGroupMemberId() {
        UUID uuid = UUID.randomUUID();
        this.userGroupMemberId = uuid.toString();
        logger.info("UserGroupMemberId issued: " + this.userGroupMemberId);
    }

    public UserGroupMemberId(String userGroupMemberId) {this.userGroupMemberId = userGroupMemberId;}

    public String getValue() {return this.userGroupMemberId;}
}
