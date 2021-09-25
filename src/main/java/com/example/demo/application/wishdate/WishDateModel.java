package com.example.demo.application.wishdate;

public class WishDateModel {

    private String wishDateId;
    private String owner;
    private String date;

    public WishDateModel(String wishDateId, String owner, String date) {

        this.wishDateId = wishDateId;
        this.owner = owner;
        this.date = date;
    }

    public String getWishDateId() {return wishDateId;}

    public String getOwner() {return owner;}

    public String getDate() {return date;}

}
