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
    private List<String> mediaPaths;
    private int likes;
    private Date dateOfCreat;

    public Post(User sender, String text, Date dateOfCreat) {
        this.sender = sender;
        this.text = text;
        this.dateOfCreat = dateOfCreat;
        this.likes = 0;
        mediaPaths = new ArrayList<String>();
    }

    public Post(User sender, String text) {
        this.sender = sender;
        this.text = text;
        this.dateOfCreat = new Date(System.currentTimeMillis());
        this.likes = 0;
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

    public int getLikes() {
        return likes;
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

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public void setDateOfCreat(Date dateOfCreat) {
        this.dateOfCreat = dateOfCreat;
    }

    public Date getDateOfCreat() {
        return dateOfCreat;
    }

    public void setMediaPaths(String[] mediaPaths) {
        this.mediaPaths.clear();
        if (mediaPaths != null) {
            Collections.addAll(this.mediaPaths, mediaPaths);
        }
    }

}