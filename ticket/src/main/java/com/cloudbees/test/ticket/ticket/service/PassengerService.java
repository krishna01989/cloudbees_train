package com.cloudbees.test.ticket.ticket.service;

import org.springframework.stereotype.Service;
import com.cloudbees.test.ticket.ticket.entity.Passenger;
import com.cloudbees.test.ticket.ticket.repository.PassengerRepository;

@Service
public class PassengerService {

    private final PassengerRepository passengerRepository;

    public PassengerService(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    public Passenger savePassenger(Passenger passenger) {
        return passengerRepository.save(passenger);
    }

    public boolean isPassengerExist(String email) {
        return passengerRepository.existByEmail(email);
    }

    public Passenger getByEmail(String email) {
        return passengerRepository.findByEmail(email);
    }

}
