package com.example.demo.presentation.wishdate;

import com.example.demo.application.wishdate.WishDateModel;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class WishDateResource {

    private String id;
    private String owner;
    private String date;
    private String groupId;

    public WishDateResource(WishDateModel wishDateModel) {
        this.id = wishDateModel.getWishDateId();
        this.owner = wishDateModel.getOwner();
        this.date = wishDateModel.getDate();
        this.groupId = wishDateModel.getUserGroupId();
    }

    public String getId() {return id;}
    public String getOwner() {return owner;}
    public String getDate() {return date;}
    public String getGroupId() {return groupId;}

}
