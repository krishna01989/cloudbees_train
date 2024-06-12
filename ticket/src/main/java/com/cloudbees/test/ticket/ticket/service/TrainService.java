package com.cloudbees.test.ticket.ticket.service;

import org.springframework.stereotype.Service;

import com.cloudbees.test.ticket.ticket.entity.Train;
import com.cloudbees.test.ticket.ticket.repository.TrainRepository;

@Service
public class TrainService {
    private final TrainRepository trainRepository;

    public TrainService(TrainRepository trainRepository) {
        this.trainRepository = trainRepository;
    }

    public Train getTrain(Long trainId) {
        return trainRepository.findById(trainId).orElseThrow(() -> new RuntimeException("Train not found"));
    }
}
