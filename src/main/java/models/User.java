package models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.*;

@Entity
public class User extends Model implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String country;
    private Date birthday;
    private Date dateOfCreat;

    public User() {
    }

    public User(String username, String firstName, String lastName, String email, String phoneNumber, String password, String country, Date birthday) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.country = country;
        this.birthday = birthday;
        this.dateOfCreat = new Date(System.currentTimeMillis());
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

    public Date getBirthday() {
        return birthday;
    }

    public Date getDateOfCreat() {
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

    public void setCountry(String country) {
        this.country = country;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setDateOfCreat(Date dateOfCreat) {
        this.dateOfCreat = dateOfCreat;
    }

}