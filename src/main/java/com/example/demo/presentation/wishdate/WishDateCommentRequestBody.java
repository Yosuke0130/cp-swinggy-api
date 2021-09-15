package com.example.demo.presentation.wishdate;

import javax.validation.constraints.NotNull;

public class WishDateCommentRequestBody {

    @NotNull
    private String author;

    @NotNull
    private String text;

    public String getAuthor() {return this.author;}
    public String getText() {return this.text;}

    public void setAuthor(String author) {this.author = author;}
    public void setText(String text) {this.text = text;}
}

