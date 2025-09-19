package com.ruralhealthcare.RHCW.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TeleconsultationDTO {
    @NotNull
    private Long patientId;
    private Long doctorId;

    @NotNull
    private String scheduledAt;
    private String notes;
}
