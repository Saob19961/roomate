package com.example.roommate.service.db;

import com.example.roommate.domain.model.Arbeitplatz;
import com.example.roommate.domain.model.Buchung;
import com.example.roommate.domain.model.User;
import com.example.roommate.domain.model.exceptions.LoginException;
import com.example.roommate.domain.model.repository.ArbeitplatzRepository;
import com.example.roommate.domain.model.repository.BlockRepository;
import com.example.roommate.domain.model.repository.BuchungRepository;
import com.example.roommate.domain.model.repository.UserRepository;
import java.sql.Time;
import java.util.Date;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ArbeitplatzRepository arbeitplatzRepository;
    @Autowired
    private BuchungRepository buchungRepository;
    @Autowired
    private BlockRepository blockrepository;

    public boolean isAdmin(String userId) {
        return userRepository.isAdmin(userId);
    }

    public User getUser(String userId) {
        return userRepository.findById(userId).orElseThrow();
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User regestrierung(User user){
        if (userRepository.findByEmail(user.getGithubadresse()) != null)
            throw new LoginException("User with similar email already exists");

        // TODO Organisation Validation
        // wir brauchen keine Validation hier , weil wir die Daten von der User hier kommt, und die ist dann gültig
        user.setId(UUID.randomUUID().toString());

        userRepository.save(user);

        return user;
    }

    public User login(String email, String password) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new LoginException("User not found with this email");
        }

        if(!user.getPassword().equals(password)) {
            throw new LoginException("Password Incorrect");
        }

        return user;
    }

    public String buchung(String user_id, String arbeitplatz_id, Date date, Time time_from, Time time_to) {
        Boolean istVerfügbar = buchungRepository.istVerfügbar(arbeitplatz_id, date, time_from, time_to);
        Boolean istBlock = blockrepository.istBlock(arbeitplatz_id, date, time_from, time_to);

        if (istBlock || !istVerfügbar) {
            throw new RuntimeException("Space already has blocked or booked");
        }

        User user = new User();
        user.setId(user_id);

        Arbeitplatz arbeitplatz = new Arbeitplatz();
        arbeitplatz.setId(arbeitplatz_id);

        Buchung buchung = new Buchung();
        buchung.setUser(user);
        buchung.setArbeitplatz(arbeitplatz);
        buchung.setData(date);
        buchung.setBuchFrom(time_from);
        buchung.setBuchTo(time_to);
        buchung.setId(UUID.randomUUID().toString());

        buchungRepository.save(buchung);

        return buchung.getId();
    }

    public void stornieren(String user_id, String buchung_id){
        buchungRepository.stornieren(user_id, buchung_id);
    }
}