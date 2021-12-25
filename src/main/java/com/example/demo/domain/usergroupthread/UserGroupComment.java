package com.example.demo.domain.usergroupthread;

public class UserGroupComment {

    private String threadId;
    private UserGroupCommentId id;
    private String memberId;
    private Text text;

    public UserGroupComment(String threadId, String memberId, String text) throws IllegalArgumentException {
        this.threadId = threadId;
        this.id = new UserGroupCommentId();
        this.memberId = memberId;
        this.text = new Text(text);
    }

    public String getThreadId() {
        return threadId;
    }

    public String getId() {
        return id.getValue();
    }

    public String getMemberId() {
        return memberId;
    }

    public String getText() {
        return text.getValue();
    }

}
