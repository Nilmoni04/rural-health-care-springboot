package com.ruralhealthcare.RHCW.service;

import com.ruralhealthcare.RHCW.dto.AmbulanceRequestDTO;
import com.ruralhealthcare.RHCW.exception.ResourceNotFoundException;
import com.ruralhealthcare.RHCW.model.AmbulanceRequest;
import com.ruralhealthcare.RHCW.model.Patient;
import com.ruralhealthcare.RHCW.repository.AmbulanceRepository;
import com.ruralhealthcare.RHCW.repository.PatientRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class AmbulanceServiceImpl implements AmbulanceService{

    private final AmbulanceRepository ambulanceRepository;
    private final PatientRepository patientRepository;

    public AmbulanceServiceImpl(AmbulanceRepository ambulanceRepository, PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
        this.ambulanceRepository = ambulanceRepository;
    }
    @Override
    public AmbulanceRequest requestAmbulance(AmbulanceRequestDTO dto) {
        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

        AmbulanceRequest request = AmbulanceRequest.builder()
                .patient(patient)
                .pickupAddress(dto.getPickupAddress())
                .destinationHospital(dto.getDestinationHospital())
                .contactNumber(dto.getContactNumber())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .status(AmbulanceRequest.AmbulanceStatus.REQUESTED)
                .build();
        return ambulanceRepository.save(request);
    }

    @Override
    public AmbulanceRequest getRequest(Long id) {
        return ambulanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ambulance request not found"));
    }

    @Override
    public List<AmbulanceRequest> getByPatient(Long patientId) {
        return ambulanceRepository.findByPatientId(patientId);
    }

    @Override
    public AmbulanceRequest updateStatus(Long id, String status, String assignedAmbulanceId) {
        AmbulanceRequest r = getRequest(id);
        r.setStatus(AmbulanceRequest.AmbulanceStatus.valueOf(status));
        if(assignedAmbulanceId != null){
            r.setAssignedAmbulanceId(assignedAmbulanceId);
        }

        return ambulanceRepository.save(r);
    }
}
