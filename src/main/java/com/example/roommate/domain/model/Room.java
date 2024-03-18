package com.example.roommate.domain.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Table
public class Room implements Serializable {
    @Id
    private String id;
    @Column
    private String name;
    @Column
    private String adresse;
    @Column
    private Integer kapatzität;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Integer getKapatzität() {
        return kapatzität;
    }

    public void setKapatzität(Integer kapatzität) {
        this.kapatzität = kapatzität;
    }
}

