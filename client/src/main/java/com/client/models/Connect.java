package com.client.models;


public class Connect {
    private int id;
    private String note;
    private String firstUsername;
    private String secondUsername;


    public Connect() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setFirstUsername(String firstUsername) {
        this.firstUsername = firstUsername;
    }

    public void setSecondUsername(String secondUsername) {
        this.secondUsername = secondUsername;
    }

    public int getId() {
        return id;
    }

    public String getNote() {
        return note;
    }

    public String getFirstUsername() {
        return firstUsername;
    }

    public String getSecondUsername() {
        return secondUsername;
    }
}
