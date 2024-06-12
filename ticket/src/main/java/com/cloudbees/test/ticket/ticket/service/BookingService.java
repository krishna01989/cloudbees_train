package com.cloudbees.test.ticket.ticket.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloudbees.test.ticket.ticket.entity.Passenger;
import com.cloudbees.test.ticket.ticket.entity.Seat;
import com.cloudbees.test.ticket.ticket.entity.Ticket;
import com.cloudbees.test.ticket.ticket.exception.ServiceException;
import com.cloudbees.test.ticket.ticket.request.validator.BookingRequestValidator;
import com.cloudbees.test.ticket.ticket.response.model.Booking;
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
        Passenger existPassenger = passengerService.getByEmail(passenger.getEmail());
        if (existPassenger == null) {
            passengerService.savePassenger(passenger);
        } else {
            passenger.setId(existPassenger.getId());
        }
        if (ticketService.isTicketExist(trainId, passenger.getEmail())) {
            throw new ServiceException("Ticket already exist for the passenger");
        }
        Seat seat = seatService.getAvailableSeat(trainId);
        if (seat == null) {
            throw new ServiceException("No available seats");
        } else {
            seat.setOccupied(true);
            return createBookingResponse(ticketService.saveTicket(this.createTicket(trainId, seat, passenger)));
        }
    }

    private BookingResponse createBookingResponse(Ticket ticket) {
        BookingResponse response = new BookingResponse();
        response.setBookings(List.of(
                Booking.builder().pnr(ticket.getPnr()).price(ticket.getPrice()).seat(ticket.getSeat().getSeatNumber())
                        .from(ticket.getFromLocation()).to(ticket.getToLocation()).status(ticket.getStatus()).build()));
        return response;
    }

    private BookingResponse createBookingResponse(List<Ticket> tickets) {
        if (tickets == null || tickets.isEmpty())
            throw new ServiceException("No tickets found");
        BookingResponse response = new BookingResponse();
        response.setBookings(tickets.stream()
                .map(ticket -> Booking.builder().pnr(ticket.getPnr()).price(ticket.getPrice())
                        .seat(ticket.getSeat().getSeatNumber()).from(ticket.getFromLocation())
                        .to(ticket.getToLocation())
                        .status(ticket.getStatus()).build())
                .collect(Collectors.toList()));
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

    public BookingResponse getByPassengerEmail(String email) {
        if (!passengerService.isPassengerExist(email)) {
            throw new ServiceException("Passenger not found");
        }
        return createBookingResponse(ticketService.getTickets(email));
    }

    @Transactional
    public void cancelBooking(Long trainId, String email) {
        if (!passengerService.isPassengerExist(email)) {
            throw new ServiceException("Passenger not found");
        } else if (!ticketService.isTicketExist(trainId, email)) {
            throw new ServiceException("Passenger has not booked tickets");
        } else {
            ticketService.updateStatusToCancelled(trainId, email);
            seatService.releaseSeat(trainId, email);
        }
    }

    @Transactional
    public BookingResponse updateSeat(String pnr) {
        Ticket ticket = ticketService.getTicketByPnr(pnr);
        if (ticket == null) {
            throw new ServiceException("Ticket not found or already cancelled");
        } else {
            Seat availableSeat = seatService.getAvailableSeat(ticket.getTrain().getId());
            if (availableSeat == null) {
                throw new ServiceException("No available seats");
            } else {
                Seat releaseTicket = ticket.getSeat();
                releaseTicket.setOccupied(false);
                seatService.saveSeat(releaseTicket);
                availableSeat.setOccupied(true);
                ticket.setSeat(availableSeat);
                ticketService.saveTicket(ticket);
                
            }
        }
        return createBookingResponse(ticket);
    }

}
