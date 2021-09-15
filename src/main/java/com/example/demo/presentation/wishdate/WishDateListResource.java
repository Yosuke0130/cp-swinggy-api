package com.example.demo.presentation.wishdate;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class WishDateListResource {
    private List<WishDateResource> wishDates;
    private int total;

    public WishDateListResource(List<WishDateResource> wishDates, int total) {
        this.wishDates = wishDates;
        this.total = total;
    }

    public List<WishDateResource> getWishDates() {
        return wishDates;
    }

    public int getTotal() {
        return total;
    }
}
