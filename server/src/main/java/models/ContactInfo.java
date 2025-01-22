package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ContactInfo extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String username;
    String email;
    String phoneNumber;
    String phoneType;
    String address;
    String birthday;
    String birthMonth;
    String birthdayDisplayPolicy;
    String instantCommunication;

    public ContactInfo() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getBirthMonth() {
        return birthMonth;
    }

    public void setBirthMonth(String birthMonth) {
        this.birthMonth = birthMonth;
    }

    public String getBirthdayDisplayPolicy() {
        return birthdayDisplayPolicy;
    }

    public void setBirthdayDisplayPolicy(String birthdayDisplayPolicy) {
        this.birthdayDisplayPolicy = birthdayDisplayPolicy;
    }

    public String getInstantCommunication() {
        return instantCommunication;
    }

    public void setInstantCommunication(String instantCommunication) {
        this.instantCommunication = instantCommunication;
    }
}
