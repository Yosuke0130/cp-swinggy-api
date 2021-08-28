package com.example.demo.presentation.wish_date;

import com.example.demo.application.wish_date.WishDateCommentModel;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.text.SimpleDateFormat;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class WishDateCommentResource {

    private String wishDateCommentId;
    private String wishDateId;
    private String author;
    private String text;
    private String createdAt;

    public WishDateCommentResource(WishDateCommentModel wishDateCommentModel) {
        this.wishDateCommentId = wishDateCommentModel.getWishDateCommentId();
        this.wishDateId = wishDateCommentModel.getWishDateId();
        this.author = wishDateCommentModel.getAuthor();
        this.text = wishDateCommentModel.getText();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.createdAt = sdf.format(wishDateCommentModel.getCreatedAt());
    }

    public String getWishDateCommentId() {return this.wishDateCommentId;}
    public String getWishDateId() {return this.wishDateId;}
    public String getAuthor() {return this.author;}
    public String getText() {return this.text;}
    public String getCreatedAt() {return this.createdAt;}

}
