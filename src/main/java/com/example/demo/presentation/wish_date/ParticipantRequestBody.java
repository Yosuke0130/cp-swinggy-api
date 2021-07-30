package com.example.demo.presentation.wish_date;

import javax.validation.constraints.NotNull;

public class ParticipantRequestBody {

    @NotNull
    private String participant;

    public String getParticipant() {return participant;}

    public void setParticipant(String participant) {this.participant = participant;}
}
