package com.example.demo.presentation.wishdate;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import javax.validation.constraints.NotNull;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class WishDateRequestBody {

    @NotNull
    private String owner;
    @NotNull
    private String date;

    private String userGroupId;

    public String getOwner() {
        return this.owner;
    }

    public String getDate() {
        return this.date;
    }

    public String getUserGroupId() {
        return userGroupId;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setUserGroupId(String userGroupId) {
        this.userGroupId = userGroupId;
    }
}
