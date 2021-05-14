package com.example.demo.domain.wish_date;

import com.example.demo.Logging;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class WishDateId {

    @Autowired
    Logging logger;

    private String wishDateId;

    public WishDateId() {

        UUID uuid = UUID.randomUUID();
        this.wishDateId= uuid.toString();
        logger.info("UUID issued: " + this.wishDateId);
    }

    public WishDateId(String wishDateId) {this.wishDateId = wishDateId;}

    public String getValue() {return this.wishDateId;}
}
