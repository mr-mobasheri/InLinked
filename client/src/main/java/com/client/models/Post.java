package com.client.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.*;

public class Post implements Serializable {
    private int id;
    private User sender;
    private String text;
    private String mediaPath;
    private long dateOfCreat;
    private List<String> likerUsername = new ArrayList<>();

    public Post(User sender, String text, long dateOfCreat) {
        this.sender = sender;
        this.text = text;
        this.dateOfCreat = dateOfCreat;
    }

    public Post(User sender, String text) {
        this.sender = sender;
        this.text = text;
        this.dateOfCreat = System.currentTimeMillis();
    }

    public Post() {
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setDateOfCreat(long dateOfCreat) {
        this.dateOfCreat = dateOfCreat;
    }

    public void setLikerUsername(List<String> likerUsername) {
        this.likerUsername = likerUsername;
    }

    public User getSender() {
        return sender;
    }

    public String getText() {
        return text;
    }

    public void setMediaPath(String mediaPath) {
        this.mediaPath = mediaPath;
    }

    public String getMediaPath() {
        return mediaPath;
    }

    public int getId() {
        return id;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getLikerUsername() {
        return likerUsername;
    }
}