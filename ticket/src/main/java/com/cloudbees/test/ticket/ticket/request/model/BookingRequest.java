package com.cloudbees.test.ticket.ticket.request.model;

import com.cloudbees.test.ticket.ticket.entity.Passenger;

import lombok.Data;

@Data
public class BookingRequest {

    private long trainId;
    private Passenger passenger;
    
}
