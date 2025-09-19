package com.ruralhealthcare.RHCW.service;

import com.ruralhealthcare.RHCW.dto.AmbulanceRequestDTO;
import com.ruralhealthcare.RHCW.model.AmbulanceRequest;

import java.util.List;

public interface AmbulanceService {
    AmbulanceRequest requestAmbulance(AmbulanceRequestDTO dto);
    AmbulanceRequest getRequest(Long id);
    List<AmbulanceRequest> getByPatient(Long patientId);
    AmbulanceRequest updateStatus(Long id, String status,String assignedAmbulanceId);
}
