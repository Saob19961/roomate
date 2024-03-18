package com.example.roommate.domain.model.repository;

import com.example.roommate.domain.model.Block;
import com.example.roommate.domain.model.Buchung;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public interface BlockRepository extends CrudRepository<Block, String> {
    @Query(
        "SELECT (count (*) != 0) FROM Block b " +
        "where b.arbeitplatz.id = :arbeitplatz_id AND b.data = :date AND " +
        " (" +
        "        (:time_from BETWEEN b.datum_from AND b.datum_to) " +
        "        OR (:time_to BETWEEN b.datum_from AND b.datum_to) " +
        "        OR (b.datum_from BETWEEN :time_from AND :time_to) " +
        "        OR (b.datum_to BETWEEN :time_from AND :time_to) " +
        "    )"
    )
    Boolean istBlock(String arbeitplatz_id, Date date, Time time_from, Time time_to);

    @Query("select b from Block b where b.user.id = :user_id order by b.data desc, b.datum_from desc")
    List<Block> getUserBlocks(String user_id);

    @Query("DELETE from Block b where b.id = :id AND b.user.id = :user_id ")
    void unBlockieren(String user_id, String id);

    @Query("SELECT b from Block b where b.arbeitplatz.id = :arbeitplatz_id ORDER BY b.data, b.datum_from")
    List<Block> getArbeitPlatzBlockList(String arbeitplatz_id);
}
