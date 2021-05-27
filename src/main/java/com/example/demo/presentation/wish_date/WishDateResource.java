package com.example.demo.presentation.wish_date;

import com.example.demo.application.wish_date.WishDateModel;

public class WishDateResource {

    private String wishDateId;
    private String owner;
    private String date;

    public WishDateResource(WishDateModel wishDateModel) {

        this.wishDateId = wishDateModel.getWishDateId();
        this.owner = wishDateModel.getOwner();
        this.date = wishDateModel.getDate();

    }

    public String getWishDateId() {return wishDateId;}

    public String getOwner() {return owner;}

    public String getDate() {return date;}
}