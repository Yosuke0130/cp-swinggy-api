package com.example.demo.presentation.wishdate;

import javax.validation.constraints.NotNull;

public class WishDateRequestBody {

    @NotNull
    private String owner;
    @NotNull
    private String date;

    private String groupId;

    public String getOwner() {return this.owner;}
    public String getDate() {return this.date;}
    public String getGroupId() {return this.groupId;}

    public void setOwner(String owner) {this.owner = owner;}
    public void setDate(String date) {this.date = date;}
    public void setGroupId(String groupId) {this.groupId = groupId;}

}
