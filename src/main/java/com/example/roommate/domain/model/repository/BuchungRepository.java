package com.example.roommate.domain.model.repository;

import com.example.roommate.domain.model.Buchung;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public interface BuchungRepository extends CrudRepository<Buchung, String> {
    @Query(
        "SELECT (count (*) = 0) FROM Buchung b " +
        "where b.arbeitplatz.id = :arbeitplatz_id AND b.data = :date AND " +
        " (" +
        "        (:time_from BETWEEN b.buch_from AND b.buch_to) " +
        "        OR (:time_to BETWEEN b.buch_from AND b.buch_to) " +
        "        OR (b.buch_from BETWEEN :time_from AND :time_to) " +
        "        OR (b.buch_to BETWEEN :time_from AND :time_to) " +
        "    )"
    )
    Boolean istVerfügbar(String arbeitplatz_id, Date date, Time time_from, Time time_to);

    @Query("DELETE from Buchung b where b.id = :id AND b.user.id = :user_id")
    void stornieren(String user_id, String id);

    @Query("select b from Buchung b where b.user.id = :user_id order by b.data desc, b.buch_from desc")
    List<Buchung> getUserReservations(String user_id);

    @Query("select b from Buchung b order by b.data desc, b.buch_from desc")
    List<Buchung> getAllReservations();
}

