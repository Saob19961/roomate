package com.example.roommate.domain.model.repository;

import com.example.roommate.domain.model.Arbeitplatz;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ArbeitplatzRepository extends CrudRepository<Arbeitplatz, String> {
    @Query("select a from Arbeitplatz a where a.room.id = :room order by a.name")
    List<Arbeitplatz> getRoomArbeitplatz(String room);

    @Query("select count(a) from Arbeitplatz a where a.room.id = :room")
    Integer getRoomCurrentSpaceCount(String room);
}
