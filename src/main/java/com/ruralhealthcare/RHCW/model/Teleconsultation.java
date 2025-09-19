package com.ruralhealthcare.RHCW.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "teleconsultations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Teleconsultation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    private LocalDateTime scheduledAt;
    private String videoLink;
    private String notes;

    @Enumerated(EnumType.STRING)
    private TeleStatus status;

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if(createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if(status == null) {
            status = TeleStatus.SCHEDULED;
        }
    }
    public enum TeleStatus{
        SCHEDULED, ONGOING, COMPLETED, CANCELLED
    }
}
