package com.ruralhealthcare.RHCW.repository;

import com.ruralhealthcare.RHCW.model.AmbulanceRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AmbulanceRepository extends JpaRepository<AmbulanceRequest, Long> {
    List<AmbulanceRequest> findByPatientId(Long patientId);
}
