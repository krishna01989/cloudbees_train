package com.cloudbees.test.ticket.ticket.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    // get list of tickets for the passenger
    public List<Ticket> getTickets(String email) {
        return ticketRepository.getByPassengerEmail(email);
    }

    @Transactional
    public void updateStatusToCancelled(Long trainId, String email) {
        ticketRepository.updateStatusToCancelled(trainId, email);
    }

    public Ticket getTicketByPnr(String pnr) {
        return ticketRepository.getByPnr(pnr);
    }

}
