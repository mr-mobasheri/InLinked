package com.client.models;

import java.io.Serializable;

public class Message implements Serializable {
    private int id;


    private String text;
    private long dateOfCreat;
    private User sender;
    private User receiver;

    public Message(String text) {
        this.text = text;
    }

    public Message() {
    }

    public Message(String text, User sender, User receiver) {
        this.text = text;
        this.dateOfCreat = System.currentTimeMillis();
        this.sender = sender;
        this.receiver = receiver;
    }

    public String getText() {
        return text;
    }

    public int getId() {
        return id;
    }

    public void setDateOfCreat(long dateOfCreat) {
        this.dateOfCreat = dateOfCreat;
    }

    public long getDateOfCreat() {
        return dateOfCreat;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

}