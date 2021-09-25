package com.example.demo.application.wishdate;

import java.sql.Timestamp;

public class ParticipationModel {

    private String participationId;
    private String wishDateId;
    private Timestamp createdAt;
    private String participant;

    public ParticipationModel(String participationId, String wishDateId, Timestamp createdAt, String participant) {

        this.participationId = participationId;
        this.wishDateId = wishDateId;
        this.createdAt = createdAt;
        this.participant = participant;
    }

    public String getParticipationId() {return participationId;}

    public String getWishDateId() {return wishDateId;}

    public Timestamp getCreatedAt() {return createdAt;}

    public String getParticipant() {return  participant;}

}
