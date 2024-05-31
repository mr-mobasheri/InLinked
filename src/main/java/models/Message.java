package models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
public class Message extends Model implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    private String text;

    private Date dateOfCreat;


    @ManyToOne()
    @JoinColumn(name = "sender_username", referencedColumnName = "username")
    private User sender;

    @ManyToOne()
    @JoinColumn(name = "receiver_id", referencedColumnName = "username")
    private User receiver;

    public Message(String text) {
        this.text = text;
    }

    public Message() {
    }

    public Message(String text, User sender, User receiver) {
        this.text = text;
        this.dateOfCreat = new Date(System.currentTimeMillis());
        this.sender = sender;
        this.receiver = receiver;
    }

    public String getText() {
        return text;
    }

    public int getId() {
        return id;
    }

    public Date getDateOfCreat() {
        return dateOfCreat;
    }

    public void setDateOfCreat(Date dateOfCreat) {
        this.dateOfCreat = dateOfCreat;
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