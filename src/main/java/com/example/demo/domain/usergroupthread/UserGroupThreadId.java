package com.example.demo.domain.usergroupthread;

import com.example.demo.Logging;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class UserGroupThreadId {

    @Autowired
    private Logging logger;

    private String id;

    public UserGroupThreadId() {
        UUID uuid = UUID.randomUUID();
        this.id = uuid.toString();
        logger.info("UserGroupThreadId issued: " + this.id);
    }

    public String getValue() {return this.id;}
}
