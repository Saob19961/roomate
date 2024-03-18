package com.example.roommate.domain.model.request;

import java.io.Serializable;
import java.util.List;

public class SpaceModel implements Serializable {
    private String id;
    private String name;
    private String room;
    private List<String> equipments;

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

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public List<String> getEquipments() {
        return equipments;
    }

    public void setEquipments(List<String> equipments) {
        this.equipments = equipments;
    }
}
