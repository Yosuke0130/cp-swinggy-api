package com.example.demo.presentation.wish_date;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ParticipationListResource {
    private List<ParticipationResource> participations;
    private int total;

    public ParticipationListResource(List<ParticipationResource> participations, int total) {
        this.participations = participations;
        this.total = total;
    }

    public List<ParticipationResource> getParticipations() {
        return participations;
    }

    public int getTotal() {
        return total;
    }
}
