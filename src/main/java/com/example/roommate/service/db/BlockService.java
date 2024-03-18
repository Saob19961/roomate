package com.example.roommate.service.db;

import com.example.roommate.domain.model.Block;
import com.example.roommate.domain.model.Buchung;
import com.example.roommate.domain.model.repository.BlockRepository;
import com.example.roommate.domain.model.repository.BuchungRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@Service
public class BlockService {
    @Autowired
    private BuchungRepository buchungRepository;
    @Autowired
    private BlockRepository blockRepository;

    public List<Block> getUserBlocks(String userId) {
        return blockRepository.getUserBlocks(userId);
    }

    public String update(String block_id, Date date, Time time_from, Time time_to) {
        Block block = blockRepository.findById(block_id).get();

        String arbeitplatz_id = block.getArbeitplatz().getId();

        Boolean istVerfügbar = buchungRepository.istVerfügbar(arbeitplatz_id, date, time_from, time_to);
        Boolean istBlock = blockRepository.istBlock(arbeitplatz_id, date, time_from, time_to);

        if (istBlock || !istVerfügbar) {
            throw new RuntimeException("Space already has blocked or booked");
        }

        block.setData(date);
        block.setDatumFrom(time_from);
        block.setDatumTo(time_to);

        blockRepository.save(block);

        return block.getId();
    }

    public void delete(String id) {
        blockRepository.deleteById(id);
    }
}