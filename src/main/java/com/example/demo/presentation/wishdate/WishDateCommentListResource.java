package com.example.demo.presentation.wishdate;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class WishDateCommentListResource {

    private List<WishDateCommentResource> wishDateComments;
    private int total;

    public WishDateCommentListResource(List<WishDateCommentResource> wishDateComments, int total) {
        this.wishDateComments = wishDateComments;
        this.total = total;
    }

    public List<WishDateCommentResource> getWishDateComments() {return this.wishDateComments;}
    public int getTotal() {return this.total;}
}
