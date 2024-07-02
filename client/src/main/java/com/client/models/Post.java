package com.client.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Post implements Serializable {
    private int id;
    private User sender;
    private String text;
    private List<String> mediaPaths = new ArrayList<>();
    private long dateOfCreat;
    private List<String> likerUsername = new ArrayList<>();
    private List<String> comments = new ArrayList<>();

    public Post(User sender, String text, long dateOfCreat) {
        this.sender = sender;
        this.text = text;
        this.dateOfCreat = dateOfCreat;
        mediaPaths = new ArrayList<String>();
    }

    public Post(User sender, String text) {
        this.sender = sender;
        this.text = text;
        this.dateOfCreat = System.currentTimeMillis();
        mediaPaths = new ArrayList<String>();
    }

    public Post() {
    }

    public User getSender() {
        return sender;
    }

    public String getText() {
        return text;
    }

    public List<String> getMediaPaths() {
        if(mediaPaths == null) {
            mediaPaths = new ArrayList<>();
        }
        return mediaPaths;
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

    public List<String> getComments() {
        return comments;
    }

    public void setMediaPath(ArrayList<String> mediaPaths) {

        if(mediaPaths != null) {
            this.mediaPaths = mediaPaths;
        }
        else {
            this.mediaPaths = new ArrayList<>();
        }

    }

    public long getDateOfCreat() {
        return dateOfCreat;
    }

    public void setMediaPaths(String[] mediaPaths) {
        this.mediaPaths.clear();
        if (mediaPaths != null) {
            Collections.addAll(this.mediaPaths, mediaPaths);
        }
    }

}