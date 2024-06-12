package com.cloudbees.test.ticket.ticket.response.model;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class PassengerManifestResponse {
    List<Manifest> manifests;
}
