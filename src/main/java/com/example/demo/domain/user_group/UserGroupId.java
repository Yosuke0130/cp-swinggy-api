package com.example.demo.domain.user_group;

import com.example.demo.Logging;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class UserGroupId {

    @Autowired
    Logging logger;

    private String userGroupId;

    public UserGroupId() {
        UUID uuid = UUID.randomUUID();
        this.userGroupId = uuid.toString();
        logger.info("UserGroupId issued: " + this.userGroupId);
    }

    public String getValue() {return this.userGroupId;}
}
