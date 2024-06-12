package com.cloudbees.test.ticket.ticket.repository;

import com.cloudbees.test.ticket.ticket.entity.Seat;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    // JPQL query to get Seat if isOccupied is false for a given train id
    String GET_AVAILABLE_SEATS = "SELECT s FROM Seat s WHERE s.section.train.id = :trainId AND s.isOccupied = false";

    // repository method to execute GET_AVAILABLE_SEATS
    @Query(GET_AVAILABLE_SEATS)
    List<Seat> availableSeats(@Param("trainId") Long trainId);
}
