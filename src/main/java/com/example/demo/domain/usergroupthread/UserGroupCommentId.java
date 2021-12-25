package com.example.demo.domain.usergroupthread;

import com.example.demo.Logging;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class UserGroupCommentId {

    @Autowired
    private Logging logger;

    private String id;

    public UserGroupCommentId() {
        UUID uuid = UUID.randomUUID();
        this.id = uuid.toString();
        logger.info("UserGroupCommentId issued: " + this.id);
    }

    public UserGroupCommentId(String id) {
        this.id = id;
    }

    public String getValue() {return this.id;}
}
