package com.upwork.controller;

import com.upwork.model.Seat;
import com.upwork.service.TicketingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/tickets")
public class TicketingController {
    private final TicketingService ticketingService;

    @Autowired
    public TicketingController(TicketingService ticketingService) {
        this.ticketingService = ticketingService;
    }

    @GetMapping("/next")
    public Seat getNextAvailableTicket(@RequestParam(required = false) Integer row, @RequestParam(required = false) Integer col) {
        Optional<Seat> preferredSeat = Optional.empty();
        if (row != null && col != null) {
            preferredSeat = Optional.of(new Seat(row, col));
        }
        return ticketingService.getNextAvailableTicket(preferredSeat);
    }

    @PostMapping("/reset")
    public ResponseEntity<?> resetSeats() {
        this.ticketingService.clearSeats();
        return ResponseEntity.status(HttpStatus.OK).body("Seats cleared");
    }
}

