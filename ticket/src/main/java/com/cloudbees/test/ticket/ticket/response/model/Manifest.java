package com.cloudbees.test.ticket.ticket.response.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Manifest {

    private String firstName;
    private String lastName;
    private String email;
    private String pnr;
    private String seat;
    
}
