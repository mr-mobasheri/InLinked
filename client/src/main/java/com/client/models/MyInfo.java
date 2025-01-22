package com.client.models;

public class MyInfo {
    int id;
    private String username;
    private String additionalName;
    private String headline;
    private String currentJobPosition;
    private String levelOfEducation;
    private String educationPlace;
    private String city;
    private String conditionn;
    private String profession;

    public MyInfo(String additionalName, String headline, String currentJobPosition,
                  String levelOfEducation, String educationPlace, String city, String condition, String profession) {
        this.additionalName = additionalName;
        this.headline = headline;
        this.currentJobPosition = currentJobPosition;
        this.levelOfEducation = levelOfEducation;
        this.educationPlace = educationPlace;
        this.city = city;
        this.conditionn = condition;
        this.profession = profession;
    }

    public MyInfo() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAdditionalName(String additionalName) {
        this.additionalName = additionalName;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public void setCurrentJobPosition(String currentJobPosition) {
        this.currentJobPosition = currentJobPosition;
    }

    public void setLevelOfEducation(String levelOfEducation) {
        this.levelOfEducation = levelOfEducation;
    }

    public void setEducationPlace(String educationPlace) {
        this.educationPlace = educationPlace;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCondition(String condition) {
        this.conditionn = condition;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public int getId() {
        return id;
    }

    public String getAdditionalName() {
        return additionalName;
    }

    public String getHeadline() {
        return headline;
    }

    public String getCurrentJobPosition() {
        return currentJobPosition;
    }

    public String getLevelOfEducation() {
        return levelOfEducation;
    }

    public String getEducationPlace() {
        return educationPlace;
    }

    public String getCity() {
        return city;
    }

    public String getCondition() {
        return conditionn;
    }

    public String getProfession() {
        return profession;
    }
}