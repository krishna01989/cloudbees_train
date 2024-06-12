package com.cloudbees.test.ticket.ticket.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.cloudbees.test.ticket.ticket.entity.Passenger;
import com.cloudbees.test.ticket.ticket.response.model.BookingResponse;
import com.cloudbees.test.ticket.ticket.service.BookingService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

class BookingControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private BookingController bookingController;

    @Mock
    private BookingService bookingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookingController).build();
    }

    @Test
    void bookTicket() throws Exception {
        BookingResponse response = new BookingResponse();
        response.setPnr("123456");
        response.setFrom("London");
        response.setTo("France");
        response.setPrice(5);
        response.setStatus("CONFIRMED");
        response.setSeat("A1");

        when(bookingService.bookTicket(any(Long.class), any(Passenger.class))).thenReturn(response);
        mockMvc.perform(post("/booking/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        "{\"trainId\":\"1\",\"passenger\":{\"firstName\":\"John\",\"lastName\":\"Doe\",\"email\":\"john.doe@doe.com\"}}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pnr").value("123456"));
    }

}
