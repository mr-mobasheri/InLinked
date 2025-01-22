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
    private String filePath;
    private String sender;
    private String receiver;

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
}