package com.cloudbees.test.ticket.ticket.request.model;

import lombok.Data;

@Data
public class PassengerManifestRequest {
    private long trainId;
    private String sectionName;
}
