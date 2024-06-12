package com.cloudbees.test.ticket.ticket.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloudbees.test.ticket.ticket.entity.Passenger;
import com.cloudbees.test.ticket.ticket.entity.Seat;
import com.cloudbees.test.ticket.ticket.entity.Ticket;
import com.cloudbees.test.ticket.ticket.exception.ServiceException;
import com.cloudbees.test.ticket.ticket.request.validator.BookingRequestValidator;
import com.cloudbees.test.ticket.ticket.response.model.BookingResponse;

@Service
public class BookingService {

    private final SeatService seatService;

    private final PassengerService passengerService;

    private final TrainService trainService;

    private final TicketService ticketService;

    private final BookingRequestValidator bookingRequestValidator;

    public BookingService(SeatService seatService, PassengerService passengerService, TrainService trainService,
            TicketService ticketService, BookingRequestValidator bookingRequestValidator) {
        this.seatService = seatService;
        this.passengerService = passengerService;
        this.trainService = trainService;
        this.ticketService = ticketService;
        this.bookingRequestValidator = bookingRequestValidator;
    }

    @Transactional
    public BookingResponse bookTicket(Long trainId, Passenger passenger) throws ServiceException {
        bookingRequestValidator.validate(trainId, passenger);
        if (ticketService.isTicketExist(trainId, passenger.getEmail())) {
            throw new ServiceException("Ticket already exist for the passenger");
        }
        Seat seat = seatService.getAvailableSeat(trainId);
        if (seat == null) {
            throw new ServiceException("No available seats");
        } else {
            seat.setOccupied(true);
            seatService.saveSeat(seat);
            passengerService.savePassenger(passenger);
            return createBookingResponse(ticketService.saveTicket(this.createTicket(trainId, seat, passenger)));
        }
    }

    private BookingResponse createBookingResponse(Ticket ticket) {
        BookingResponse response = new BookingResponse();
        response.setPnr(ticket.getPnr());
        response.setPrice(ticket.getPrice());
        response.setSeat(ticket.getSeat().getSeatNumber());
        response.setFrom(ticket.getFromLocation());
        response.setTo(ticket.getToLocation());
        response.setStatus(ticket.getStatus());
        return response;
    }

    private Ticket createTicket(Long trainId, Seat seat, Passenger passenger) {
        Ticket ticket = new Ticket();
        ticket.setPassenger(passenger);
        ticket.setSeat(seat);
        ticket.setTrain(trainService.getTrain(trainId));
        ticket.setFromLocation("London");
        ticket.setToLocation("France");
        ticket.setPrice(5.00);
        ticket.setStatus("CONFIRMED");
        // create a PNR number using current date and time
        ticket.setPnr("" + System.currentTimeMillis());
        return ticket;
    }

}
