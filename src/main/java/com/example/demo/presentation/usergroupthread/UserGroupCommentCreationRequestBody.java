package com.example.demo.presentation.usergroupthread;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import javax.validation.constraints.NotNull;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserGroupCommentCreationRequestBody {
    @NotNull
    private String memberId;
    @NotNull
    private String text;

    public UserGroupCommentCreationRequestBody(String memberId, String text) {
        this.memberId = memberId;
        this.text = text;
    }

    public String getMemberId() {
        return memberId;
    }

    public String getText() {
        return text;
    }

}
