package com.cloudbees.test.ticket.ticket.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fromLocation;
    private String toLocation;
    private double price;
    private String status;
    private String pnr;

    @ManyToOne
    private Passenger passenger;

    @ManyToOne
    private Train train;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Seat seat;

}
