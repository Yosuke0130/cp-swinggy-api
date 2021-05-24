package com.example.demo.domain.wish_date;

import java.time.LocalDateTime;

public class Participation {

    private ParticipationId participationId;
    private WishDateId wishDateId;
    private LocalDateTime date;
    private String participant;

    public Participation(String wishDateId, String participant) {

        this.participationId = new ParticipationId();
        this.wishDateId = new WishDateId(wishDateId);
        this.date = LocalDateTime.now();
        this.participant = participant;
    }

    public Participation(String participationId, String wishDateId, String date, String participant) {

        this.participationId = new ParticipationId(participationId);
        this.wishDateId = new WishDateId(wishDateId);
        this.date = LocalDateTime.parse(date);
        this.participant = participant;

    }


    public String getParticipationId() {return participationId.getValue();}

    public String getWishDateId() {return wishDateId.getValue();}

    public LocalDateTime getDate() {return date;}

    public String getParticipant() {return participant;}

}
