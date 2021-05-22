package com.example.demo.application.wish_date;

import java.time.LocalDateTime;

public class ParticipationModel {

    private String participationId;
    private String wishDateId;
    private LocalDateTime date;
    private String participant;

    public ParticipationModel(String participationId, String wishDateId, LocalDateTime date, String participant) {

        this.participationId = participationId;
        this.wishDateId = wishDateId;
        this.date = date;
        this.participant = participant;
    }

    public String getParticipationId() {return participationId;}

    public String getWishDateId() {return wishDateId;}

    public LocalDateTime getDate() {return date;}

    public String getParticipant() {return  participant;}

}
