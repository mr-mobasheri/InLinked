package com.client.models;

import java.io.Serializable;

public class Message implements Serializable {
    private int id;


    private String text;
    private long dateOfCreat;
    private String sender;
    private String receiver;
    private String filePath;

    public Message(String text) {
        this.text = text;
    }

    public Message() {
    }

    public Message(String text, User sender, User receiver) {
        this.text = text;
        this.dateOfCreat = System.currentTimeMillis();
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
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

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }
}