package com.example.demo.application.usergroupthread;

import java.sql.Timestamp;

public class UserGroupCommentQueryModel {

    private String id;
    private String userGroupThreadId;
    private String memberId;
    private String text;
    private Timestamp createdAt;

    public UserGroupCommentQueryModel(String id, String userGroupThreadId, String memberId, String text, Timestamp createdAt) {
        this.id = id;
        this.userGroupThreadId = userGroupThreadId;
        this.memberId = memberId;
        this.text = text;
        this.createdAt = createdAt;
    }

    public String getId() { return id; }

    public String getUserGroupThreadId() { return userGroupThreadId; }

    public String getMemberId() {
        return memberId;
    }

    public String getText() {
        return text;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }
}
