package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Skills extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    String username;
    String skills1;
    String skills2;
    String skills3;
    String skills4;
    String skills5;

    public Skills(){
    }

    public Skills(String username, String skills1, String skills2, String skills3, String skills4, String skills5) {
        this.username = username;
        this.skills1 = skills1;
        this.skills2 = skills2;
        this.skills3 = skills3;
        this.skills4 = skills4;
        this.skills5 = skills5;
    }

    public int getId() {
        return id;
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

    public String getSkills1() {
        return skills1;
    }

    public void setSkills1(String skills1) {
        this.skills1 = skills1;
    }

    public String getSkills2() {
        return skills2;
    }

    public void setSkills2(String skills2) {
        this.skills2 = skills2;
    }

    public String getSkills3() {
        return skills3;
    }

    public void setSkills3(String skills3) {
        this.skills3 = skills3;
    }

    public String getSkills4() {
        return skills4;
    }

    public void setSkills4(String skills4) {
        this.skills4 = skills4;
    }

    public String getSkills5() {
        return skills5;
    }

    public void setSkills5(String skills5) {
        this.skills5 = skills5;
    }
}
