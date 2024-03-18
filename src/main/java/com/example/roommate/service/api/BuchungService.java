package com.example.roommate.service.api;

import com.example.roommate.domain.model.Buchung;
import com.example.roommate.domain.model.User;
import com.example.roommate.domain.model.request.BookingModel;
import com.example.roommate.domain.model.request.LoginModel;
import com.example.roommate.domain.model.request.UpdateBookingModel;
import com.example.roommate.service.db.BookingService;
import com.example.roommate.service.db.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/buchung")
public class BuchungService {
    @Autowired
    private UserService userService;

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public String buchung(@RequestBody BookingModel bookingModel) {
        return userService.buchung(
            bookingModel.getUser_id(),
            bookingModel.getArbeitplatz_id(),
            bookingModel.getDate(),
            bookingModel.getTime_from(),
            bookingModel.getTime_to()
        );
    }

    @PutMapping
    public String updateBuchung(@RequestBody UpdateBookingModel bookingModel) {
        return bookingService.update(
            bookingModel.getBook_id(),
            bookingModel.getDate(),
            bookingModel.getTime_from(),
            bookingModel.getTime_to()
        );
    }

    @DeleteMapping("/{bookId}")
    public void deleteBooking(@PathVariable String bookId) {
        bookingService.delete(bookId);
    }
}
