package com.upwork.service;

import com.upwork.model.Cinema;
import com.upwork.model.Seat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TicketingServiceTest {

    @Autowired
    private TicketingService ticketingService;

    @Autowired
    private Cinema cinema;

    @BeforeEach
    public void setup() {
        // Reset cinema seats before each test
        for (int i = 0; i < cinema.getSeats().length; i++) {
            for (int j = 0; j < cinema.getSeats()[0].length; j++) {
                cinema.getSeat(i, j).setOccupied(false);
            }
        }
    }

    @Test
    public void testGetNextAvailableTicketWithoutPreferredSeat() {
        Seat seat = ticketingService.getNextAvailableTicket(Optional.empty());
        assertNotNull(seat);
        assertTrue(seat.getRow() >= 0 && seat.getRow() < cinema.getSeats().length);
        assertTrue(seat.getCol() >= 0 && seat.getCol() < cinema.getSeats()[0].length);
        assertTrue(seat.isOccupied());
    }

    @Test
    public void testGetNextAvailableTicketWithPreferredSeatAvailable() {
        // Assume seat at (2, 2) is available

        Optional<Seat> preferredSeat = Optional.of(cinema.getSeat(2, 2));

        Seat seat = ticketingService.getNextAvailableTicket(preferredSeat);
        assertNotNull(seat);
        assertEquals(2, seat.getRow());
        assertEquals(2, seat.getCol());
        assertTrue(seat.isOccupied());
    }

    @Test
    public void testGetNextAvailableTicketWithPreferredSeatOccupied() {
        // Assume seat at (2, 2) is occupied
        cinema.occupySeat(2, 2); // Occupy seat at (2, 2)
        Optional<Seat> preferredSeat = Optional.of(cinema.getSeat(2, 2));

        // Occupy nearby seats to force allocation elsewhere
        cinema.occupySeat(1, 1); // Occupy seat above (2, 2)
        cinema.occupySeat(1, 3); // Occupy seat above (2, 2)
        cinema.occupySeat(3, 1); // Occupy seat below (2, 2)
        cinema.occupySeat(3, 3); // Occupy seat below (2, 2)

        Seat seat = ticketingService.getNextAvailableTicket(preferredSeat);
        assertNotNull(seat);
        assertNotEquals(2, seat.getRow());
        assertNotEquals(2, seat.getCol());
        assertTrue(seat.isOccupied());
    }

    @Test
    public void testGetNextAvailableTicketWithAllPreferredSeatsOccupied() {
        // Occupy all seats around the preferred seat (2, 2)
        cinema.occupySeat(1, 2);
        cinema.occupySeat(3, 2);
        cinema.occupySeat(2, 1);
        cinema.occupySeat(2, 3);

        Optional<Seat> preferredSeat = Optional.of(cinema.getSeat(2, 2));

        // The next available seat should be allocated
        Seat seat = ticketingService.getNextAvailableTicket(preferredSeat);
        assertNotNull(seat);
        assertTrue(seat.getRow() >= 0 && seat.getRow() < cinema.getSeats().length);
        assertTrue(seat.getCol() >= 0 && seat.getCol() < cinema.getSeats()[0].length);
        assertTrue(seat.isOccupied());
    }

    @Test
    public void testClearSeatsShouldClearSeats(){
        cinema.occupySeat(1, 2);
        cinema.occupySeat(3, 2);
        cinema.occupySeat(2, 1);
        cinema.occupySeat(2, 3);

        ticketingService.clearSeats();

        assertFalse(cinema.getSeat(1,2).isOccupied());
        assertFalse(cinema.getSeat(3,2).isOccupied());
        assertFalse(cinema.getSeat(2,1).isOccupied());
        assertFalse(cinema.getSeat(2,3).isOccupied());

    }
}

