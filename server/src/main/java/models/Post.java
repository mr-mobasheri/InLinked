package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.*;

@Entity
public class Post extends Model implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne()
    @JoinColumn(referencedColumnName = "username")
    private User sender;
    private String text;

    private String mediaPath;

    @ElementCollection
    @CollectionTable(joinColumns = @JoinColumn)
    private List<String> likerUsername = new ArrayList<>();

    private long dateOfCreat;

    public Post(User sender, String text, long dateOfCreat) {
        this.sender = sender;
        this.text = text;
        this.dateOfCreat = dateOfCreat;
    }

    public Post(User sender, String text) {
        this.sender = sender;
        this.text = text;
        this.dateOfCreat = System.currentTimeMillis();
    }

    public Post() {
    }

    public int getId() {
        return id;
    }

    public void setLikerUsername(List<String> likerUsername) {
        this.likerUsername = likerUsername;
    }

    public void setDateOfCreat(long dateOfCreat) {
        this.dateOfCreat = dateOfCreat;
    }

    public User getSender() {
        return sender;
    }

    public String getText() {
        return text;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getLikerUsername() {
        return likerUsername;
    }

    public long getDateOfCreat() {
        return dateOfCreat;
    }

    public void setMediaPath(String mediaPath) {
        this.mediaPath = mediaPath;
    }

    public String getMediaPath() {
        return mediaPath;
    }

    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

}