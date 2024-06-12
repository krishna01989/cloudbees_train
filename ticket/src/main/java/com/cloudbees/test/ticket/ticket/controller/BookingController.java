package com.cloudbees.test.ticket.ticket.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloudbees.test.ticket.ticket.request.model.BookingRequest;
import com.cloudbees.test.ticket.ticket.response.model.BookingResponse;
import com.cloudbees.test.ticket.ticket.service.BookingService;

@RestController
@RequestMapping("booking")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("create")
    public BookingResponse bookTicket(@RequestBody BookingRequest request) {
        return bookingService.bookTicket(request.getTrainId(), request.getPassenger());
    }

    @PostMapping("fetch")
    public BookingResponse fetchBooking(@RequestBody BookingRequest request) {
        return bookingService.getByPassengerEmail(request.getPassenger().getEmail());
    }

    @DeleteMapping("cancel")
    public void cancelBooking(@RequestBody BookingRequest request) {
        bookingService.cancelBooking(request.getTrainId(), request.getPassenger().getEmail());
    }

    @PostMapping("update/{pnr}")
    public BookingResponse updateSeat(@PathVariable String pnr) {
        return bookingService.updateSeat(pnr);
    }

}
