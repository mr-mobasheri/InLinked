package models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
public class Post extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne()
    @JoinColumn(referencedColumnName = "username")
    private User sender;
    private String text;
    @ElementCollection
    @CollectionTable(joinColumns = @JoinColumn)
    private List<String> mediaPaths = new ArrayList<>();

    @ElementCollection
    @CollectionTable(joinColumns = @JoinColumn)
    private List<String> comments = new ArrayList<>();

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
        mediaPaths = new ArrayList<String>();
    }

    public Post() {
    }

    public User getSender() {
        return sender;
    }

    public String getText() {
        return text;
    }

    public List<String> getMediaPaths() {
        if(mediaPaths == null) {
            mediaPaths = new ArrayList<>();
        }
        return mediaPaths;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setMediaPaths(ArrayList<String> mediaPaths) {
        this.mediaPaths = mediaPaths;
    }

    public List<String> getComments() {
        return comments;
    }

    public List<String> getLikerUsername() {
        return likerUsername;
    }

    public long getDateOfCreat() {
        return dateOfCreat;
    }

    public void setMediaPaths(String[] mediaPaths) {
        this.mediaPaths.clear();
        if (mediaPaths != null) {
            Collections.addAll(this.mediaPaths, mediaPaths);
        }
    }

}