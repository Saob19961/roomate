package com.example.roommate.domain.model;

import jakarta.persistence.*;

import java.sql.Time;
import java.util.Date;

@Entity
@Table
public class Buchung {
    @Id
    private String id;
    @Column
    private Time buch_to;
    @Column
    private Time buch_from;
    @Column
    private Date data;
    @OneToOne
    @JoinColumn(name="arbeitplatz_id")
    private Arbeitplatz arbeitplatz;
    @OneToOne
    @JoinColumn(name="user_id")
    private User user;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Time getBuchTo() {
        return buch_to;
    }

    public void setBuchTo(Time buch_to) {
        this.buch_to = buch_to;
    }

    public Time getBuchFrom() {
        return buch_from;
    }

    public void setBuchFrom(Time buch_from) {
        this.buch_from = buch_from;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Arbeitplatz getArbeitplatz() {
        return arbeitplatz;
    }

    public void setArbeitplatz(Arbeitplatz arbeitplatz) {
        this.arbeitplatz = arbeitplatz;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
