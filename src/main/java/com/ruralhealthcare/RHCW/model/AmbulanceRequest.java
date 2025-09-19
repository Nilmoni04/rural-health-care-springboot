package com.ruralhealthcare.RHCW.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "ambulance_request")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AmbulanceRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    private String pickupAddress;
    private String destinationHospital;
    private String contactNumber;
    private Double latitude;
    private Double longitude;

    @Enumerated(EnumType.STRING)
    private AmbulanceStatus status;
    private String assignedAmbulanceId;
    private LocalDateTime requestedAt;

    @PrePersist
    public void prePersist(){
        if(requestedAt == null) {
            requestedAt = LocalDateTime.now();
        }
        if(status == null) {
            status = AmbulanceStatus.REQUESTED;
        }
    }
    public enum AmbulanceStatus {
        REQUESTED, DISPATCHED, ARRIVED, COMPLETED, CANCELLED
    }
}
