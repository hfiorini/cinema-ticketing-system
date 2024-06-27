package com.upwork.service;

import com.upwork.model.Cinema;
import com.upwork.model.Seat;

import java.util.Arrays;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.concurrent.locks.ReentrantLock;

public class TicketingService {
    private final PriorityQueue<Seat> availableSeats;
    private final Cinema cinema;

    public TicketingService(Cinema cinema) {
        this.cinema = cinema;
        int centerX = cinema.getRows() / 2;
        int centerY = cinema.getCols() / 2;
        availableSeats = new PriorityQueue<>(new SeatComparator(centerX, centerY));
        initializeAvailableSeats();
    }

    private void initializeAvailableSeats() {
        for (int i = 0; i < cinema.getRows(); i++) {
            for (int j = 0; j < cinema.getCols(); j++) {
                Seat seat = cinema.getSeat(i, j);
                if (!seat.isOccupied()) {
                    availableSeats.offer(seat);
                }
            }
        }
    }

    public synchronized Seat getNextAvailableTicket(Optional<Seat> preferredSeat) {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        try {
            if (preferredSeat.isPresent()) {
                Seat seat = preferredSeat.get();

                boolean available = cinema.isAvailable(seat.getRow(), seat.getCol());
                if (available) {
                    availableSeats.remove(seat);
                    cinema.occupySeat(seat.getRow(), seat.getCol());
                    seat.setOccupied(true);

                    return seat;
                }
            }

            // Allocate the next available seat
            while (!availableSeats.isEmpty()) {
                Seat seat = availableSeats.poll();
                boolean available = cinema.isAvailable(seat.getRow(), seat.getCol());
                if (available) {
                    cinema.occupySeat(seat.getRow(), seat.getCol());
                    seat.setOccupied(true);
                    return seat;
                }
            }

        } finally {
            lock.unlock();
        }
        return null; // No available seats
    }


    public void clearSeats() {
        Arrays.stream(cinema.getSeats()).forEach(rows -> Arrays.stream(rows).forEach(seat -> seat.setOccupied(false)));
    }
}

