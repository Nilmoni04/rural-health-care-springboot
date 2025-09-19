package com.ruralhealthcare.RHCW.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AppointmentDTO {
    @NotNull
    private Long patientId;

    @NotNull
    private Long doctorId;

    @Size(max = 1000)
    private String reason;
    @NotNull
    private String appointmentAt;
}
