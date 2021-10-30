package com.example.demo.presentation.usergroupthread;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import javax.validation.constraints.NotNull;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserGroupThreadCreationRequestBody {
    @NotNull
    private String name;

    public UserGroupThreadCreationRequestBody(@NotNull String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
