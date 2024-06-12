package com.cloudbees.test.ticket.ticket.config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import com.cloudbees.test.ticket.ticket.entity.Seat;
import com.cloudbees.test.ticket.ticket.entity.Section;
import com.cloudbees.test.ticket.ticket.entity.Train;
import com.cloudbees.test.ticket.ticket.repository.TrainRepository;

import jakarta.annotation.PostConstruct;

@Component
public class DataInitializer {

    private final TrainRepository trainRepository;

    public DataInitializer(TrainRepository trainRepository) {
        this.trainRepository = trainRepository;
    }

    @PostConstruct
    public void init() {
        // Initialize Train
        Train train = new Train();
        train.setId(1L);
        // Initialize Sections
        Section sectionA = new Section();
        sectionA.setName("A");
        sectionA.setTrain(train);
        Section sectionB = new Section();
        sectionB.setName("B");
        sectionB.setTrain(train);
        train.setSections(List.of(sectionA, sectionB));
        List<Seat> seatsA = new ArrayList<>();
        List<Seat> seatsB = new ArrayList<>();
        // Initialize Seats
        for (int i = 1; i <= 10; i++) {
            Seat seat = new Seat();
            seat.setSeatNumber("A" + i);
            seat.setOccupied(false);
            seat.setSection(sectionA);
            seatsA.add(seat);
        }
        for (int i = 1; i <= 10; i++) {
            Seat seat = new Seat();
            seat.setSeatNumber("B" + i);
            seat.setOccupied(false);
            seat.setSection(sectionB);
            seatsB.add(seat);
        }
        sectionA.setSeats(seatsA);
        sectionB.setSeats(seatsB);
        trainRepository.save(train);

    }
}
