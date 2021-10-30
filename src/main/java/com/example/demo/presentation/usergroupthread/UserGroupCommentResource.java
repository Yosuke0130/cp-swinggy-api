package com.example.demo.presentation.usergroupthread;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserGroupCommentResource {
    private String id;
    private String userGroupThreadId;
    private String memberId;
    private String text;
    private String createdAt;

    public UserGroupCommentResource(String id, String userGroupThreadId, String memberId, String text, String createdAt) {
        this.id = id;
        this.userGroupThreadId = userGroupThreadId;
        this.memberId = memberId;
        this.text = text;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public String getUserGroupThreadId() {
        return userGroupThreadId;
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
