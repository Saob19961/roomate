package com.example.roommate.domain.model;

import jakarta.persistence.*;

import java.sql.Time;
import java.util.Date;

@Entity
@Table
public class Block {
    @Id
    private String id;
    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User user;
    @ManyToOne
    @JoinColumn(name="arbeitplatz_id", referencedColumnName = "id")
    private Arbeitplatz arbeitplatz;
    @Column
    private Date data;
    @Column
    private Time datum_from;
    @Column
    private Time datum_to;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Arbeitplatz getArbeitplatz() {
        return arbeitplatz;
    }

    public void setArbeitplatz(Arbeitplatz arbeitplatz) {
        this.arbeitplatz = arbeitplatz;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Time getDatumFrom() {
        return datum_from;
    }

    public void setDatumFrom(Time datum_from) {
        this.datum_from = datum_from;
    }

    public Time getDatumTo() {
        return datum_to;
    }

    public void setDatumTo(Time datum_to) {
        this.datum_to = datum_to;
    }
}
