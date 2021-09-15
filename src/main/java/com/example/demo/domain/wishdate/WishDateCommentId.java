package com.example.demo.domain.wishdate;

import com.example.demo.Logging;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class WishDateCommentId {

    @Autowired
    Logging logger;

    private String wishDateCommentId;

    public WishDateCommentId() {

        UUID uuid = UUID.randomUUID();
        this.wishDateCommentId= uuid.toString();
        logger.info("WishDateCommentId issued: " + this.wishDateCommentId);
    }

    public WishDateCommentId(String wishDateCommentId) { this.wishDateCommentId = wishDateCommentId;}


    public String getValue() {return this.wishDateCommentId;}
}
