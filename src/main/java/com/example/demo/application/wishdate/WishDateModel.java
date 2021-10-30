package com.example.demo.application.wishdate;

public class WishDateModel {

    private String wishDateId;
    private String owner;
    private String date;
    private String userGroupId;

    public WishDateModel(String wishDateId, String owner, String date, String userGroupId) {
        this.wishDateId = wishDateId;
        this.owner = owner;
        this.date = date;
        this.userGroupId = userGroupId;
    }

    public String getWishDateId() {return this.wishDateId;}
    public String getOwner() {return this.owner;}
    public String getDate() {return this.date;}
    public String getUserGroupId() {return this.userGroupId;}

}
