package com.client.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable{
    @JsonProperty("id")
    private int id;
    @JsonProperty("username")

    private String username;
    @JsonProperty("password")

    private String password;
    @JsonProperty("firstName")

    private String firstName;
    @JsonProperty("lastName")

    private String lastName;
    @JsonProperty("email")

    private String email;
    @JsonProperty("phoneNumber")

    private String phoneNumber;
    @JsonProperty("country")

    private String country;
    @JsonProperty("birthday")

    private long birthday;
    @JsonProperty("dateOfCreat")

    private long dateOfCreat;
    @JsonProperty("profilePath")

    private String profilePath;
    @JsonIgnore
    private List<Message> sentMessages = new ArrayList<>();
    @JsonIgnore
    private List<Message> receivedMessages = new ArrayList<>();
    @JsonIgnore
    private List<Post> posts = new ArrayList<>();

    public User() {
    }

    public User(String username, String password, String firstName, String lastName, String email,
                String phoneNumber, String country, long birthday) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.country = country;
        this.birthday = birthday;
        this.dateOfCreat = System.currentTimeMillis();
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCountry() {
        return country;
    }

    public long getBirthday() {
        return birthday;
    }

    public long getDateOfCreat() {
        return dateOfCreat;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public void setDateOfCreat(long dateOfCreat) {
        this.dateOfCreat = dateOfCreat;
    }

    public List<Message> getReceivedMessages() {
        return receivedMessages;
    }

    public void setReceivedMessages(List<Message> receivedMessages) {
        this.receivedMessages = receivedMessages;
    }

    public List<Message> getSentMessages() {
        return sentMessages;
    }

    public void setSentMessages(List<Message> sentMessages) {
        this.sentMessages = sentMessages;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }

    public String getProfilePath() {
        return profilePath;
    }

}

