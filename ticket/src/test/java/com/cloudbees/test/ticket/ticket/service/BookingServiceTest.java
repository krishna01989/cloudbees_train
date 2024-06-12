package com.cloudbees.test.ticket.ticket.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.cloudbees.test.ticket.ticket.entity.Passenger;
import com.cloudbees.test.ticket.ticket.entity.Seat;
import com.cloudbees.test.ticket.ticket.entity.Ticket;
import com.cloudbees.test.ticket.ticket.request.validator.BookingRequestValidator;
import com.cloudbees.test.ticket.ticket.response.model.BookingResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookingServiceTest {

    @InjectMocks
    private BookingService bookingService;

    @Mock
    private TrainService trainService;

    @Mock
    private TicketService ticketService;

    @Mock
    private SeatService seatService;

    @Mock
    private PassengerService passengerService;

    @Mock
    private BookingRequestValidator bookingRequestValidator;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        Seat seat = new Seat();
        seat.setSeatNumber("A1");
        seat.setId(1L);
        seat.setOccupied(false);
        seat.setSection(null);
        Mockito.when(seatService.getAvailableSeat(1L)).thenReturn(seat);
        Ticket ticket = new Ticket();
        ticket.setPnr("123456");
        ticket.setPrice(100.0);
        ticket.setSeat(seat);
        ticket.setFromLocation("Delhi");
        ticket.setToLocation("Mumbai");
        ticket.setStatus("CONFIRMED");
        Mockito.when(ticketService.saveTicket(any(Ticket.class))).thenReturn(ticket);
    }

    @Test
    void testBookTicket() {
        Long trainId = 1L;
        Passenger passenger = new Passenger();
        passenger.setFirstName("John");
        passenger.setLastName("Doe");
        passenger.setEmail("john.doe@doe.com");

        when(ticketService.isTicketExist(trainId, passenger.getEmail())).thenReturn(false);

        BookingResponse response = bookingService.bookTicket(trainId, passenger);

        assertNotNull(response);
        verify(bookingRequestValidator, times(1)).validate(trainId, passenger);
        verify(ticketService, times(1)).isTicketExist(trainId, passenger.getEmail());
        verify(seatService, times(1)).getAvailableSeat(trainId);
        verify(passengerService, times(1)).savePassenger(passenger);
    }
}
