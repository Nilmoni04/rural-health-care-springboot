package com.ruralhealthcare.RHCW.repository;

import com.ruralhealthcare.RHCW.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
