package com.cloudbees.test.ticket.ticket.response.model;

import java.util.List;

import lombok.Data;

@Data
public class BookingResponse {

    List<Booking> bookings;
    
}
