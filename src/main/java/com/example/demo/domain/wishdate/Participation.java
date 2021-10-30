package com.example.demo.domain.wishdate;

import java.sql.Timestamp;

public class Participation {

    private ParticipationId participationId;
    private WishDateId wishDateId;
    private Timestamp createdAt;
    private String participant;

    public Participation(String wishDateId, String participant) {

        this.participationId = new ParticipationId();
        this.wishDateId = new WishDateId(wishDateId);
        this.participant = participant;
    }

    public Participation(String participationId, String wishDateId, Timestamp createdAt, String participant) {

        this.participationId = new ParticipationId(participationId);
        this.wishDateId = new WishDateId(wishDateId);
        this.createdAt = createdAt;
        this.participant = participant;
    }


    public String getParticipationId() {return this.participationId.getValue();}

    public String getWishDateId() {return this.wishDateId.getValue();}

    public Timestamp getCreatedAt() {return this.createdAt;}

    public String getParticipant() {return this.participant;}

}
