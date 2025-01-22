package models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Connect extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String note;
    private String firstUsername;
    private String secondUsername;
    private boolean isConnected;

    public Connect() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setFirstUsername(String firstUsername) {
        this.firstUsername = firstUsername;
    }

    public void setSecondUsername(String secondUsername) {
        this.secondUsername = secondUsername;
    }

    public int getId() {
        return id;
    }

    public String getNote() {
        return note;
    }

    public String getFirstUsername() {
        return firstUsername;
    }

    public String getSecondUsername() {
        return secondUsername;
    }
}
