package com.example.demo.presentation.wishdate;

import com.example.demo.application.wishdate.ParticipationModel;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.sql.Timestamp;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ParticipationResource {

    private String participationId;
    private String wishDateId;
    private Timestamp createdAt;
    private String participant;

    public ParticipationResource(ParticipationModel participationModel) {

        this.participationId = participationModel.getParticipationId();
        this.wishDateId = participationModel.getWishDateId();
        this.createdAt = participationModel.getCreatedAt();
        this.participant = participationModel.getParticipant();
    }

    public String getParticipationId() {return participationId;}

    public String getWishDateId() {return wishDateId;}

    public Timestamp getCreatedAt() {return createdAt;}

    public String getParticipant() {return  participant;}

}
