package com.ruralhealthcare.RHCW.repository;

import com.ruralhealthcare.RHCW.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}
