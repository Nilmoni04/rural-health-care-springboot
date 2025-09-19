package com.ruralhealthcare.RHCW.service;

import com.ruralhealthcare.RHCW.dto.AppointmentDTO;
import com.ruralhealthcare.RHCW.exception.ResourceNotFoundException;
import com.ruralhealthcare.RHCW.model.Appointment;
import com.ruralhealthcare.RHCW.model.Doctor;
import com.ruralhealthcare.RHCW.model.Patient;
import com.ruralhealthcare.RHCW.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
@Transactional
public class AppointmentServiceImpl implements AppointmentService{

    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;

    public AppointmentServiceImpl(PatientRepository patientRepository, AppointmentRepository appointmentRepository,
                                  DoctorRepository doctorRepository) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    @Override
    public Appointment bookAppointment(AppointmentDTO dto) {
        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));

        LocalDateTime dateTime;
        try {
            dateTime = LocalDateTime.parse(dto.getAppointmentAt());
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid datetime format. Use ISO-8601 like 2025-09-24T10:00");
        }

        Appointment a = Appointment.builder().
                patient(patient)
                .doctor(doctor)
                .appointmentAt(dateTime)
                .reason(dto.getReason())
                .status(Appointment.AppointmentStatus.PENDING)
                .build();
        return appointmentRepository.save(a);
    }

    @Override
    public Appointment getAppointment(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Appointment not found"));
    }

    @Override
    public List<Appointment> getAppointmentByPatient(Long patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }

    @Override
    public Appointment updateStatus(Long id, String status) {
        Appointment ap = getAppointment(id);
        ap.setStatus(Appointment.AppointmentStatus.valueOf(status));
        return appointmentRepository.save(ap);
    }
}
