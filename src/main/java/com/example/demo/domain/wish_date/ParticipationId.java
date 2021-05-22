package com.example.demo.domain.wish_date;

import com.example.demo.Logging;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class ParticipationId {

    @Autowired
    Logging logger;

    private String participationId;

    public ParticipationId() {

        UUID uuid = UUID.randomUUID();
        this.participationId = uuid.toString();
        logger.info("ParticipationId issued: " + this.participationId);
    }

    public ParticipationId(String participationId) {this.participationId = participationId;}

    public String getValue() {return this.participationId;}
}
