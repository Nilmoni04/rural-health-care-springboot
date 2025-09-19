package com.ruralhealthcare.RHCW.service;

import com.ruralhealthcare.RHCW.dto.TeleconsultationDTO;
import com.ruralhealthcare.RHCW.model.Teleconsultation;

import java.util.List;

public interface TeleconsultationService {
    Teleconsultation schedule(TeleconsultationDTO dto);
    Teleconsultation get(Long id);
    List<Teleconsultation> getByPatient(Long patientId);
    Teleconsultation assignDoctorAndStart(Long id, Long doctorId);
    Teleconsultation updateStatus(Long id, String status);
}
