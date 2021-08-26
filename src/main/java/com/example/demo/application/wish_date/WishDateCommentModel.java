package com.example.demo.application.wish_date;

import java.sql.Timestamp;

public class WishDateCommentModel {

    private String wishDateCommentId;
    private String wishDateId;
    private String author;
    private String text;
    private Timestamp created_at;

    public WishDateCommentModel(String wishDateCommentId, String wishDateId, String author, String text, Timestamp created_at) {
        this.wishDateCommentId = wishDateCommentId;
        this.wishDateId = wishDateId;
        this.author = author;
        this.text = text;
        this.created_at = created_at;
    }

    public String getWishDateCommentId() {return this.wishDateCommentId;}
    public String getWishDateId() {return this.wishDateId;}
    public String getAuthor() {return this.author;}
    public String getText() {return this.text;}
    public Timestamp getCreated_at() {return this.created_at;}
}
