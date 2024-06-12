package com.cloudbees.test.ticket.ticket.response.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Booking {
    private String pnr;
    private String status;
    private String from;
    private String to;
    private double price;
    private String seat;  
}
