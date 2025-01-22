package com.client.models;

public class Comment {
    private int id;
    private String postID;
    private String text;
    private String senderUsername;
    private String senderProfilePath;

    public Comment() {
    }

    public Comment(String text, String senderUsername, String senderProfilePath) {
        this.text = text;
        this.senderUsername = senderUsername;
        this.senderProfilePath = senderProfilePath;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getPostID() {
        return postID;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public void setSenderProfilePath(String senderProfilePath) {
        this.senderProfilePath = senderProfilePath;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public String getSenderProfilePath() {
        return senderProfilePath;
    }
}
