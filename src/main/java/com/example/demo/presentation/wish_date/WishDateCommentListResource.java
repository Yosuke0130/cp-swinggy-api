package com.example.demo.presentation.wish_date;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class WishDateCommentListResource {

    private List<WishDateCommentResource> comments;
    private int total;

    public WishDateCommentListResource(List<WishDateCommentResource> comments, int total) {
        this.comments = comments;
        this.total = total;
    }

    public List<WishDateCommentResource> getComments() {return this.comments;}
    public int getTotal() {return this.total;}
}
