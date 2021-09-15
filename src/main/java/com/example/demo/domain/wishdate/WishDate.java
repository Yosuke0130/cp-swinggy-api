package com.example.demo.domain.wishdate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class WishDate {

    private WishDateId wishDateId;
    private String owner;
    private LocalDate date;

    public WishDate(String owner, String date) throws IllegalArgumentException {
        try {

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate validatedDate = LocalDate.parse(date, dtf);

            this.wishDateId = new WishDateId();
            this.owner = owner;
            this.date = validatedDate;

        } catch (DateTimeParseException e) {

            throw new IllegalArgumentException("Date format error.");
        }

    }

    public WishDate(String wishDateId, String owner, String date) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate validatedDate = LocalDate.parse(date, dtf);

        this.wishDateId = new WishDateId(wishDateId);
        this.owner = owner;
        this.date = validatedDate;
    }

    public String getWishDateId() {return this.wishDateId.getValue();}

    public String getOwner() {return this.owner;}

    public LocalDate getDate() {return this.date;}

}
