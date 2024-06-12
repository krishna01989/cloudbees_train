package com.cloudbees.test.ticket.ticket.service;

import org.springframework.stereotype.Service;

import com.cloudbees.test.ticket.ticket.entity.Ticket;
import com.cloudbees.test.ticket.ticket.repository.TicketRepository;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Ticket saveTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    // check if ticket already exist for the passenger
    public boolean isTicketExist(Long trainId, String email) {
        return ticketRepository.existsByPassengerEmail(trainId, email);
    }

}
