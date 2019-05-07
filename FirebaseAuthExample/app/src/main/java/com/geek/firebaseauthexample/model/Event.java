package com.geek.firebaseauthexample.model;

import java.io.Serializable;

public class Event implements Serializable {
    int id;
    String title;
    String agenda;
    String attendees;
    long timestamp;

    public Event(int id, String title, String agenda, String attendees, long timestamp) {
        this.id = id;
        this.title = title;
        this.agenda = agenda;
        this.attendees = attendees;
        this.timestamp = timestamp;
    }

    public Event() {

    }

    public Event(int id, String title, String agenda, String attendees) {
        this.id = id;
        this.title = title;
        this.agenda = agenda;
        this.attendees = attendees;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAgenda() {
        return agenda;
    }

    public void setAgenda(String agenda) {
        this.agenda = agenda;
    }

    public String getAttendees() {
        return attendees;
    }

    public void setAttendees(String attendees) {
        this.attendees = attendees;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
