package com.example.demo.presentation.usergroup;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import javax.validation.constraints.NotNull;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserGroupRequestBody {

    @NotNull
    private String name;

    @NotNull
    private String owner;

    public String getName() {return this.name;}
    public String getOwner() {return this.owner;}
}
