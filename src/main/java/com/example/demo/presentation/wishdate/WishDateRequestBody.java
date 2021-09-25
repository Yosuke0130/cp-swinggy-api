package com.example.demo.presentation.wishdate;

import javax.validation.constraints.NotNull;

public class WishDateRequestBody {

    @NotNull
    private String owner;
    @NotNull
    private String date;

    public String getOwner() {return this.owner;}
    public String getDate() {return this.date;}

    public void setOwner(String owner) {this.owner = owner;}
    public void setDate(String date) {this.date = date;}

}
