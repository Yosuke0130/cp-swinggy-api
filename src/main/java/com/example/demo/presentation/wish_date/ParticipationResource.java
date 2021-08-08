package com.example.demo.presentation.wish_date;

import com.example.demo.application.wish_date.ParticipationModel;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.time.LocalDateTime;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ParticipationResource {

    private String participationId;
    private String wishDateId;
    private LocalDateTime createdAt;
    private String participant;

    public ParticipationResource(ParticipationModel participationModel) {

        this.participationId = participationModel.getParticipationId();
        this.wishDateId = participationModel.getWishDateId();
        this.createdAt = participationModel.getDate();
        this.participant = participationModel.getParticipant();
    }

    public String getParticipationId() {return participationId;}

    public String getWishDateId() {return wishDateId;}

    public LocalDateTime getCreatedAt() {return createdAt;}

    public String getParticipant() {return  participant;}

}
