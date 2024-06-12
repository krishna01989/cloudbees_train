package com.cloudbees.test.ticket.ticket.repository;

import com.cloudbees.test.ticket.ticket.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {

    // JPQL query to get Passenger by email id
    String EXISTS_BY_EMAIL = "SELECT COUNT(p) > 0 FROM Passenger p WHERE p.email = :email";

    // repository method to execute EXISTS_BY_EMAIL
    @Query(EXISTS_BY_EMAIL)
    Passenger findByEmail(@Param("email") String email);
}
