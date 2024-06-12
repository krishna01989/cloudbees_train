package com.cloudbees.test.ticket.ticket.service;

import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.cloudbees.test.ticket.ticket.entity.Seat;
import com.cloudbees.test.ticket.ticket.repository.SeatRepository;

@Service
public class SeatService {

    private final SeatRepository seatRepository;
    private final Random random;

    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
        this.random = new Random();
    }

    public Seat getAvailableSeat(Long trainId) {
        List<Seat> availableSeats = seatRepository.availableSeats(trainId);
        if (availableSeats.isEmpty())
            return null;
        // get random Seat from availableSeats list
        int randomIndex = random.nextInt(availableSeats.size());
        return availableSeats.get(randomIndex);
    }

    public Seat saveSeat(Seat seat) {
        return seatRepository.save(seat);
    }

}
