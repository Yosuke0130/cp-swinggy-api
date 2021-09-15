package com.example.demo.presentation.usergroup;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import javax.validation.constraints.NotNull;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserGroupRequestBody {

    @NotNull
    private String name;

    @NotNull
    private String createdBy;

    public String getName() {return this.name;}
    public String getCreatedBy() {return this.createdBy;}
}
