package com.cloudbees.test.ticket.ticket.request.validator;

import org.springframework.stereotype.Component;

import com.cloudbees.test.ticket.ticket.entity.Passenger;
import com.cloudbees.test.ticket.ticket.exception.RequestValidationException;

@Component
public class BookingRequestValidator {

    public void validate(Long trainId, Passenger passenger) {
        if (trainId == null || trainId <= 0) {
            throw new RequestValidationException("Invalid train ID");
        }
        if (passenger == null) {
            throw new RequestValidationException("Passenger details are missing");
        }
        if (passenger.getFirstName() == null || passenger.getFirstName().isEmpty()) {
            throw new RequestValidationException("Passenger First Name is missing");
        }
        if (passenger.getLastName() == null || passenger.getLastName().isEmpty()) {
            throw new RequestValidationException("Passenger Last Name is missing");
        }
        if (passenger.getEmail() == null || passenger.getEmail().isEmpty()) {
            throw new RequestValidationException("Passenger Email is missing");
        }
    }
}
