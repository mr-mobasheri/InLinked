package com.client.models;

public class Education {
    private int id;
    String username;
    String nameOfTheSchool;
    String fieldOfStudy;
    long startStudying;
    long endOfEducation;
    String grade;
    String activities;
    String description;

    public Education(){
    }
    public Education(String username, String nameOfTheSchool, String fieldOfStudy, long startStudying,
                     long endOfEducation, String grade, String activities, String description) {
        this.username = username;
        this.nameOfTheSchool = nameOfTheSchool;
        this.fieldOfStudy = fieldOfStudy;
        this.startStudying = startStudying;
        this.endOfEducation = endOfEducation;
        this.grade = grade;
        this.activities = activities;
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setNameOfTheSchool(String nameOfTheSchool) {
        this.nameOfTheSchool = nameOfTheSchool;
    }

    public void setFieldOfStudy(String fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
    }

    public void setStartStudying(long startStudying) {
        this.startStudying = startStudying;
    }

    public void setEndOfEducation(long endOfEducation) {
        this.endOfEducation = endOfEducation;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setActivities(String activities) {
        this.activities = activities;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getNameOfTheSchool() {
        return nameOfTheSchool;
    }

    public String getFieldOfStudy() {
        return fieldOfStudy;
    }

    public long getStartStudying() {
        return startStudying;
    }

    public long getEndOfEducation() {
        return endOfEducation;
    }

    public String getGrade() {
        return grade;
    }

    public String getActivities() {
        return activities;
    }

    public String getDescription() {
        return description;
    }
}
