package com.example.demo.application.wishdate;

import java.sql.Timestamp;

public class WishDateCommentModel {

    private String wishDateCommentId;
    private String wishDateId;
    private String author;
    private String text;
    private Timestamp createdAt;

    public WishDateCommentModel(String wishDateCommentId, String wishDateId, String author, String text, Timestamp createdAt) {
        this.wishDateCommentId = wishDateCommentId;
        this.wishDateId = wishDateId;
        this.author = author;
        this.text = text;
        this.createdAt = createdAt;
    }

    public String getWishDateCommentId() {return this.wishDateCommentId;}
    public String getWishDateId() {return this.wishDateId;}
    public String getAuthor() {return this.author;}
    public String getText() {return this.text;}
    public Timestamp getCreatedAt() {return this.createdAt;}
}
