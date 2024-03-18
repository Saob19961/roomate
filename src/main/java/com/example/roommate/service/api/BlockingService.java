package com.example.roommate.service.api;

import com.example.roommate.domain.model.request.BookingModel;
import com.example.roommate.domain.model.request.UpdateBookingModel;
import com.example.roommate.service.db.AdminService;
import com.example.roommate.service.db.BlockService;
import com.example.roommate.service.db.BookingService;
import com.example.roommate.service.db.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/block")
public class BlockingService {
    @Autowired
    private AdminService userService;

    @Autowired
    private BlockService blockService;

    @PostMapping
    public String block(@RequestBody BookingModel bookingModel) {
        return userService.blockieren(
            bookingModel.getUser_id(),
            bookingModel.getArbeitplatz_id(),
            bookingModel.getDate(),
            bookingModel.getTime_from(),
            bookingModel.getTime_to()
        );
    }

    @PutMapping
    public String updateBlock(@RequestBody UpdateBookingModel bookingModel) {
        return blockService.update(
            bookingModel.getBook_id(),
            bookingModel.getDate(),
            bookingModel.getTime_from(),
            bookingModel.getTime_to()
        );
    }

    @DeleteMapping("/{blockId}")
    public void deleteBlock(@PathVariable String bookId) {
        blockService.delete(bookId);
    }
}
