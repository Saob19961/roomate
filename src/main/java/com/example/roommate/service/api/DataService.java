package com.example.roommate.service.api;

import com.example.roommate.domain.model.Arbeitplatz;
import com.example.roommate.domain.model.Ausstatung;
import com.example.roommate.domain.model.Room;
import com.example.roommate.domain.model.User;
import com.example.roommate.domain.model.repository.ArbeitplatzRepository;
import com.example.roommate.domain.model.repository.AusstatungRepository;
import com.example.roommate.domain.model.repository.RoomRepository;
import com.example.roommate.domain.model.request.LoginModel;
import com.example.roommate.domain.model.request.SpaceModel;
import com.example.roommate.service.db.UserService;
import com.example.roommate.service.utils.ListHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/data/")
public class DataService {
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private ArbeitplatzRepository arbeitplatzRepository;
    @Autowired
    private AusstatungRepository ausstatungRepository;

    @GetMapping("/rooms/{roomId}/arbeitplatz")
    public List<Arbeitplatz> roomArbeitplatz(@PathVariable String roomId) {
        return arbeitplatzRepository.getRoomArbeitplatz(roomId);
    }

    @GetMapping("/rooms")
    public List<Room> rooms() {
        return ListHelper.toList(roomRepository.findAll());
    }

    @PostMapping("/rooms")
    public String createRoom(@RequestBody Room room) {
        room.setId(UUID.randomUUID().toString());
        roomRepository.save(room);
        return room.getId();
    }

    @DeleteMapping("/rooms/{roomId}")
    public void deleteRoom(@PathVariable String roomId) {
        roomRepository.deleteById(roomId);
    }

    @PutMapping("/rooms")
    public void updateRoom(@RequestBody Room room) {
        roomRepository.save(room);
    }

    @GetMapping("/equipments")
    public List<Ausstatung> equipments() {
        return ListHelper.toList(ausstatungRepository.findAll());
    }

    @PostMapping("/equipments")
    public String createEquipment(@RequestBody Ausstatung ausstatung) {
        ausstatungRepository.save(ausstatung);
        return ausstatung.getId();
    }

    @DeleteMapping("/equipments/{equipmentId}")
    public void deleteEquipment(@PathVariable String equipmentId) {
        ausstatungRepository.deleteById(equipmentId);
    }

    @PutMapping("/equipments")
    public void updateEquipments(@RequestBody Ausstatung ausstatung) {
        ausstatungRepository.save(ausstatung);
    }

    @PutMapping("/arbeitplatz")
    public void updateSpace(@RequestBody SpaceModel spaceModel) {
        Arbeitplatz arbeitplatz = arbeitplatzRepository.findById(spaceModel.getId()).get();

        Set<Ausstatung> ausstatungSet = spaceModel
            .getEquipments()
            .stream()
            .map(id -> {
                Ausstatung tmp = new Ausstatung();
                tmp.setId(id);
                return tmp;
            })
            .collect(Collectors.toSet());

        arbeitplatz.setName(spaceModel.getName());
        arbeitplatz.setAusstatungList(ausstatungSet);

        arbeitplatzRepository.save(arbeitplatz);
    }

    @PostMapping("/arbeitplatz")
    public void createSpace(@RequestBody SpaceModel spaceModel) {
        Room room = roomRepository.findById(spaceModel.getRoom()).get();
        if(Objects.equals(
            room.getKapatzität(),
            arbeitplatzRepository.getRoomCurrentSpaceCount(spaceModel.getRoom())
        )) {
            throw new RuntimeException("Room " + room.getName() + " capacity has filled");
        }

        Arbeitplatz arbeitplatz = new Arbeitplatz();
        arbeitplatz.setId(UUID.randomUUID().toString());
        arbeitplatz.setRoom(room);

        Set<Ausstatung> ausstatungSet = spaceModel
                .getEquipments()
                .stream()
                .map(id -> {
                    Ausstatung tmp = new Ausstatung();
                    tmp.setId(id);
                    return tmp;
                })
                .collect(Collectors.toSet());

        arbeitplatz.setName(spaceModel.getName());
        arbeitplatz.setAusstatungList(ausstatungSet);

        arbeitplatzRepository.save(arbeitplatz);
    }
}
