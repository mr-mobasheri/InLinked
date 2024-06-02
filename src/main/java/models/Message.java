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
    private long dateOfCreat;

    @ManyToOne()
    @JoinColumn(referencedColumnName = "username")
    private User sender;

    @ManyToOne()
    @JoinColumn(referencedColumnName = "username")
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