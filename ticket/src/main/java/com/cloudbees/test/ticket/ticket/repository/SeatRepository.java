package com.cloudbees.test.ticket.ticket.repository;

import com.cloudbees.test.ticket.ticket.entity.Seat;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    // JPQL query to get Seat if isOccupied is false for a given train id
    String GET_AVAILABLE_SEATS = "SELECT s FROM Seat s WHERE s.section.train.id = :trainId AND s.isOccupied = false";

    // repository method to execute GET_AVAILABLE_SEATS
    @Query(GET_AVAILABLE_SEATS)
    List<Seat> availableSeats(@Param("trainId") Long trainId);

    // JPQL query to change is_occupied of Seat to false for given ticket for
    // passenger email id and train id
    String UPDATE_SEAT_STATUS = "UPDATE Seat s SET s.isOccupied = false WHERE s.id in (SELECT t.seat.id FROM Ticket t WHERE t.passenger.email = :email and t.train.id = :trainId and t.status='CONFIRMED')";

    // repository method to execute UPDATE_SEAT_STATUS
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query(UPDATE_SEAT_STATUS)
    void releaseSeat(@Param("trainId") Long trainId, @Param("email") String email);

}
