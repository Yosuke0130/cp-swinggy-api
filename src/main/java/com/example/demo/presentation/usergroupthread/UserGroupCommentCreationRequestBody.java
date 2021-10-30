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
    @NotNull
    private String createdAt;

    public UserGroupCommentCreationRequestBody(String memberId, String text, String createdAt) {
        this.memberId = memberId;
        this.text = text;
        this.createdAt = createdAt;
    }

    public String getMemberId() {
        return memberId;
    }

    public String getText() {
        return text;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
