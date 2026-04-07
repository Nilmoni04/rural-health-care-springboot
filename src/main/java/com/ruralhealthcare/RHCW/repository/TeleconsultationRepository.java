package com.ruralhealthcare.RHCW.repository;

import com.ruralhealthcare.RHCW.model.Teleconsultation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeleconsultationRepository extends JpaRepository<Teleconsultation, Long> {
    List<Teleconsultation> findByPatientId(Long patientId);
}
