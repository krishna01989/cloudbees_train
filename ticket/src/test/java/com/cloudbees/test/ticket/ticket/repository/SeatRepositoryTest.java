package com.cloudbees.test.ticket.ticket.repository;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import com.cloudbees.test.ticket.ticket.entity.Seat;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class SeatRepositoryTest {

    @Autowired
    private SeatRepository seatRepository;

    @Test
    @Sql("/data.sql")
    void testAvailableSeats() {
        Seat seat = new Seat();
        seat.setSeatNumber("A1");
        seat.setId(1L);
        seat.setOccupied(false);
        seat.setSection(null);
        List<Seat> seats = seatRepository.availableSeats(1L);
        assertThat(seats).isNotNull().hasSizeGreaterThan(1);
    }
}
