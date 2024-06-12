package com.cloudbees.test.ticket.ticket.repository;

import com.cloudbees.test.ticket.ticket.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    // JPQL query to get Ticket if passenger id is equal to given passenger id with
    // status BOOKED
    String EXISTS_BY_PASSENGER_TRAIN_ID = "SELECT COUNT(t) > 0 FROM Ticket t WHERE t.passenger.id = :passengerId and t.train.id = :trainId and t.status='BOOKED'";

    // repository method to execute EXISTS_BY_PASSENGER_TRAIN_ID
    @Query(EXISTS_BY_PASSENGER_TRAIN_ID)
    boolean existsByPassengerId(@Param("trainId") Long trainId, @Param("passengerId") Long passengerId);

    // JPQL query to get Ticket for passenger email id
    String EXISTS_BY_PASSENGER_EMAIL_TRAIN_ID = "SELECT COUNT(t) > 0 FROM Ticket t WHERE t.passenger.email = :email and t.train.id = :trainId and t.status='BOOKED'";

    // repository method to execute EXISTS_BY_PASSENGER_EMAIL_TRAIN_ID
    @Query(EXISTS_BY_PASSENGER_EMAIL_TRAIN_ID)
    boolean existsByPassengerEmail(@Param("trainId") Long trainId, @Param("email") String email);

}
