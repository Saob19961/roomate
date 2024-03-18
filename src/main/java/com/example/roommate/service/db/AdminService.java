package com.example.roommate.service.db;

import com.example.roommate.domain.model.*;
import com.example.roommate.domain.model.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class AdminService {
    @Autowired
    private ArbeitplatzRepository arbeitplatzRepository;
    @Autowired
    private AusstatungRepository ausstatungRepository;
    @Autowired
    private BlockRepository blockRepository;
    @Autowired
    private BuchungRepository buchungrepository;

    public void addAusstatung(Ausstatung ausstatung) {
        if (ausstatungRepository.findById(ausstatung.getId()).isPresent())
            return;

        ausstatungRepository.save(ausstatung);
    }

    public void addAusstatungToArbeitPlatz(String arbeitPlatz_id, Ausstatung ausstatung) {
        addAusstatung(ausstatung);

        Arbeitplatz arbeitplatz = arbeitplatzRepository.findById(arbeitPlatz_id).orElseThrow();
        arbeitplatz.addAusstatung(ausstatung);

        arbeitplatzRepository.save(arbeitplatz);
    }

    public void deleteAusstatungToArbeitPlatz(String arbeitplatz_id, String ausstatugn_id) {
        Arbeitplatz arbeitplatz = arbeitplatzRepository.findById(arbeitplatz_id).orElseThrow();
        arbeitplatz.removeAusstatung(ausstatugn_id);
    }

    public String blockieren(String user_id, String arbeitplatz_id, Date date, Time time_from, Time time_to) {
        Boolean istVerfügbar = buchungrepository.istVerfügbar(arbeitplatz_id, date, time_from, time_to);
        Boolean istBlock = blockRepository.istBlock(arbeitplatz_id, date, time_from, time_to);

        if (istBlock || !istVerfügbar) {
            throw new RuntimeException("Space already blocked or booked");
        }

        User user = new User();
        user.setId(user_id);

        Arbeitplatz arbeitplatz = new Arbeitplatz();
        arbeitplatz.setId(arbeitplatz_id);

        Block block = new Block();
        block.setUser(user);
        block.setArbeitplatz(arbeitplatz);
        block.setData(date);
        block.setDatumFrom(time_from);
        block.setDatumTo(time_to);
        block.setId(UUID.randomUUID().toString());

        blockRepository.save(block);

        return block.getId();
    }

    public void unBlockieren(String user_id, String block_id) {
        blockRepository.unBlockieren(user_id, block_id);
    }

    public List <Block> getArbeitplatzBlockList(String arbeitplatz_id) {
        return blockRepository.getArbeitPlatzBlockList(arbeitplatz_id);
    }
}
