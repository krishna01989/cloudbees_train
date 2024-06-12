package com.cloudbees.test.ticket.ticket.response.model;

import lombok.Data;

@Data
public class BookingResponse {

    private String pnr;
    private String status;
    private String from;
    private String to;
    private double price;
    private String seat;
    
}
