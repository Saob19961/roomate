package com.example.roommate.service.db;

import com.example.roommate.domain.model.Arbeitplatz;
import com.example.roommate.domain.model.Buchung;
import com.example.roommate.domain.model.User;
import com.example.roommate.domain.model.exceptions.LoginException;
import com.example.roommate.domain.model.repository.ArbeitplatzRepository;
import com.example.roommate.domain.model.repository.BlockRepository;
import com.example.roommate.domain.model.repository.BuchungRepository;
import com.example.roommate.domain.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class BookingService {
    @Autowired
    private BuchungRepository buchungRepository;
    @Autowired
    private BlockRepository blockrepository;

    public String update(String book_id, Date date, Time time_from, Time time_to) {
        Buchung buchung = buchungRepository.findById(book_id).get();

        String arbeitplatz_id = buchung.getArbeitplatz().getId();

        Boolean istVerfügbar = buchungRepository.istVerfügbar(arbeitplatz_id, date, time_from, time_to);
        Boolean istBlock = blockrepository.istBlock(arbeitplatz_id, date, time_from, time_to);

        if (istBlock || !istVerfügbar) {
            throw new RuntimeException("Space already has blocked or booked");
        }

        buchung.setData(date);
        buchung.setBuchFrom(time_from);
        buchung.setBuchTo(time_to);

        buchungRepository.save(buchung);

        return buchung.getId();
    }

    public void delete(String id) {
        buchungRepository.deleteById(id);
    }

    public List<Buchung> getAllReservations() {
        return buchungRepository.getAllReservations();
    }

    public List<Buchung> getUserReservations(String userId) {
        return buchungRepository.getUserReservations(userId);
    }
}