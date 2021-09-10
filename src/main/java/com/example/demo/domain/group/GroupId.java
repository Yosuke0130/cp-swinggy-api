package com.example.demo.domain.group;

import com.example.demo.Logging;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class GroupId {

    @Autowired
    Logging logger;

    private String groupId;

    public GroupId() {
        UUID uuid = UUID.randomUUID();
        this.groupId = uuid.toString();
        logger.info("GroupId issued: " + this.groupId);
    }

    public String getValue() {return this.groupId;}
}
