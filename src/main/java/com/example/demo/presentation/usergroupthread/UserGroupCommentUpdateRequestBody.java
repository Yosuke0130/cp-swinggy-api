package com.example.demo.presentation.usergroupthread;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import javax.validation.constraints.NotNull;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserGroupCommentUpdateRequestBody {
    @NotNull
    private String text;

    public UserGroupCommentUpdateRequestBody(String memberId, String text, String createdAt) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

}
