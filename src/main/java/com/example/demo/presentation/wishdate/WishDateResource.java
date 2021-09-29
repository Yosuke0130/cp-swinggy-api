package com.example.demo.presentation.wishdate;

import com.example.demo.application.wishdate.WishDateModel;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class WishDateResource {

    private String wishDateId;
    private String owner;
    private String date;
    private String groupId;

    public WishDateResource(WishDateModel wishDateModel) {
        this.wishDateId = wishDateModel.getWishDateId();
        this.owner = wishDateModel.getOwner();
        this.date = wishDateModel.getDate();
        this.groupId = wishDateModel.getUserGroupId();
    }

    public String getWishDateId() {return wishDateId;}
    public String getOwner() {return owner;}
    public String getDate() {return date;}
    public String getGroupId() {return groupId;}

}
