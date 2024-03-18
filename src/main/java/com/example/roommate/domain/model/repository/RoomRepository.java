package com.example.roommate.domain.model.repository;

import com.example.roommate.domain.model.Room;
import org.springframework.data.repository.CrudRepository;

public interface RoomRepository extends CrudRepository<Room, String> {
}
