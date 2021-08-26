package com.example.demo.presentation.wish_date;

import com.example.demo.application.wish_date.WishDateCommentModel;

import java.text.SimpleDateFormat;

public class WishDateCommentResource {

    private String wishDateCommentId;
    private String wishDateId;
    private String author;
    private String text;
    private String created_at;

    public WishDateCommentResource(WishDateCommentModel wishDateCommentModel) {
        this.wishDateCommentId = wishDateCommentModel.getWishDateCommentId();
        this.wishDateId = wishDateCommentModel.getWishDateId();
        this.author = wishDateCommentModel.getAuthor();
        this.text = wishDateCommentModel.getText();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.created_at = sdf.format(wishDateCommentModel.getCreated_at());
    }

    public String getWishDateCommentId() {return this.wishDateCommentId;}
    public String getWishDateId() {return this.wishDateId;}
    public String getAuthor() {return this.author;}
    public String getText() {return this.text;}
    public String getCreated_at() {return this.created_at;}

}
