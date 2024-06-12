package com.cloudbees.test.ticket.ticket.repository;

import com.cloudbees.test.ticket.ticket.entity.Ticket;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    // JPQL query to get Ticket if passenger id is equal to given passenger id with
    // status CONFIRMED
    String EXISTS_BY_PASSENGER_TRAIN_ID = "SELECT COUNT(t) > 0 FROM Ticket t WHERE t.passenger.id = :passengerId and t.train.id = :trainId and t.status='CONFIRMED'";

    // repository method to execute EXISTS_BY_PASSENGER_TRAIN_ID
    @Query(EXISTS_BY_PASSENGER_TRAIN_ID)
    boolean existsByPassengerId(@Param("trainId") Long trainId, @Param("passengerId") Long passengerId);

    // JPQL query to get Ticket for passenger email id and train id with status
    // CONFIRMED
    String EXISTS_BY_PASSENGER_EMAIL_TRAIN_ID = "SELECT COUNT(t) > 0 FROM Ticket t WHERE t.passenger.email = :email and t.train.id = :trainId and t.status='CONFIRMED'";

    // repository method to execute EXISTS_BY_PASSENGER_EMAIL_TRAIN_ID
    @Query(EXISTS_BY_PASSENGER_EMAIL_TRAIN_ID)
    boolean existsByPassengerEmail(@Param("trainId") Long trainId, @Param("email") String email);

    // JPQL query to get Ticket for passenger email id
    String GET_BY_PASSENGER_EMAIL_ID = "SELECT t FROM Ticket t WHERE t.passenger.email = :email";

    // repository method to execute GET_BY_PASSENGER_EMAIL_ID
    @Query(GET_BY_PASSENGER_EMAIL_ID)
    List<Ticket> getByPassengerEmail(@Param("email") String email);

    // JPQL query to update status of ticket to CANCELLED for passenger email id and
    // train id
    String UPDATE_STATUS_TO_CANCELLED = "UPDATE Ticket t SET t.status = 'CANCELLED' WHERE t.passenger.email = :email and t.train.id = :trainId";

    // repository method to execute UPDATE_STATUS_TO_CANCELLED
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query(UPDATE_STATUS_TO_CANCELLED)
    void updateStatusToCancelled(@Param("trainId") Long trainId, @Param("email") String email);

    // JPQL query to get Ticket by pnr
    String GET_BY_PNR_ID = "SELECT t FROM Ticket t WHERE t.pnr = :pnr and t.status='CONFIRMED'";

    // repository method to execute GET_BY_PNR_ID
    @Query(GET_BY_PNR_ID)
    Ticket getByPnr(@Param("pnr") String pnr);

    // JPQL query to get Ticket by Train id and Section name
    String GET_BY_TRAIN_ID_AND_SECTION_NAME = "SELECT t FROM Ticket t WHERE t.train.id = :trainId and t.seat.section.name = :sectionName and t.status='CONFIRMED'";

    // repository method to execute GET_BY_TRAIN_ID_AND_SECTION_NAME
    @Query(GET_BY_TRAIN_ID_AND_SECTION_NAME)
    List<Ticket> getByTrainIdAndSectionName(@Param("trainId") Long trainId, @Param("sectionName") String sectionName);

}
