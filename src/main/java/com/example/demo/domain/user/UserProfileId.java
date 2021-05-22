package com.example.demo.domain.user;

import com.example.demo.Logging;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class UserProfileId {

    @Autowired
    Logging logger;

    private String userProfileId;

    public UserProfileId() {
        //UUID発行
        UUID uuid = UUID.randomUUID();
        this.userProfileId = uuid.toString();
        logger.info("UserProfileId issued: " + this.userProfileId);
    }

    public UserProfileId(String userProfileId) {
        this.userProfileId = userProfileId;
    }

    public String getValue() {return this.userProfileId;}

}
