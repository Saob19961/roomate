package com.example.roommate.service.api;

import com.example.roommate.domain.model.*;
import com.example.roommate.domain.model.repository.*;
import com.example.roommate.service.db.BlockService;
import com.example.roommate.service.db.BookingService;
import com.example.roommate.service.db.UserService;
import com.example.roommate.service.utils.ListHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users/")
public class UserDataService {
    @Autowired
    private UserService userService;
    @Autowired
    private BookingService bookingService;
    @Autowired
    private BlockService blockService;

    @GetMapping("/{userId}/reservations")
    public List<Buchung> userReservations(@PathVariable String userId) {
        if(userService.isAdmin(userId))
            return bookingService.getAllReservations();
        return bookingService.getUserReservations(userId);
    }

    @GetMapping("/{userId}/blocks")
    public List<Block> userBlocks(@PathVariable String userId) {
        return blockService.getUserBlocks(userId);
    }
}
