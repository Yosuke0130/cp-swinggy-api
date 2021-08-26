package com.example.demo.domain.wish_date;

import java.sql.Timestamp;

public class WishDateComment {

    private WishDateCommentId wishDateCommentId;
    private String wishDateId;
    private String author;
    private Text text;
    private Timestamp createdAt;

    public WishDateComment(String wishDateId, String author, String text) throws IllegalStateException {
        this.wishDateCommentId = new WishDateCommentId();
        this.wishDateId = wishDateId;
        this.author = author;
        this.text = new Text(text);
    }

    public WishDateComment(String wishDateCommentId, String wishDateId, String author, String text, Timestamp createdAt) {
        this.wishDateCommentId = new WishDateCommentId(wishDateCommentId);
        this.wishDateId = wishDateId;
        this.author = author;
        this.text = new Text(text);
        this.createdAt = createdAt;
    }

    public String getWishDateCommentId() {return this.wishDateCommentId.getValue();}
    public String getWishDateId() {return this.wishDateId;}
    public String getAuthor() {return this.author;}
    public String getText() {return this.text.getValue();}
    public Timestamp getCreated_at() {return this.createdAt;}
}
