package com.ruralhealthcare.RHCW.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AmbulanceRequestDTO {
    @NotNull
    private Long patientId;

    @NotBlank
    private String pickupAddress;
    private String destinationHospital;

    @NotBlank
    private String contactNumber;

    private Double latitude;
    private Double longitude;
}
