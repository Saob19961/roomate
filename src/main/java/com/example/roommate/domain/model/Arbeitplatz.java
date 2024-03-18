package com.example.roommate.domain.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table
public class Arbeitplatz {
    @Id
    private String id;
    @Column
    private String name;
    @ManyToOne
    @JoinColumn(name="room_id")
    private Room room;
    @ManyToMany
    private Set<Ausstatung> ausstatungList;

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

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room_id) {
        this.room = room_id;
    }

    public void setAusstatungList(Set<Ausstatung> ausstatungList) {
        this.ausstatungList = ausstatungList;
    }

    public Set<Ausstatung> getAusstatungList() {
        return ausstatungList;
    }

    public void addAusstatung(Ausstatung ausstatung) {
        if(this.ausstatungList == null)
            this.ausstatungList = new HashSet<>();

        this.ausstatungList.add(ausstatung);
    }

    public void removeAusstatung(String ausstatungId) {
        if(this.ausstatungList == null)
            return;

        this.ausstatungList.removeIf(ausstatung -> ausstatung.getId().equals(ausstatungId));
    }
}
