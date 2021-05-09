package com.example.demo.domain.user;

import com.example.demo.Logging;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class UserProfileId {

    @Autowired
    Logging logger;

    private String userprofileId;

    public UserProfileId() {
        //UUID発行
        UUID uuid = UUID.randomUUID();
        this.userprofileId = uuid.toString();
        logger.info("UUID issued: " + this.userprofileId);
    }

    public String getValue() {return this.userprofileId;}

}
