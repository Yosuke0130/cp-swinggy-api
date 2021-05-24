package com.example.demo.presentation.wish_date;

import com.example.demo.application.wish_date.ParticipationModel;

import java.time.LocalDateTime;

public class ParticipationResource {

    private String participationId;
    private String wishDateId;
    private LocalDateTime date;
    private String participant;

    public ParticipationResource(ParticipationModel participationModel) {

        this.participationId = participationModel.getParticipationId();
        this.wishDateId = participationModel.getWishDateId();
        this.date = participationModel.getDate();
        this.participant = participationModel.getParticipant();
    }

    public String getParticipationId() {return participationId;}

    public String getWishDateId() {return wishDateId;}

    public LocalDateTime getDate() {return date;}

    public String getParticipant() {return  participant;}

}
