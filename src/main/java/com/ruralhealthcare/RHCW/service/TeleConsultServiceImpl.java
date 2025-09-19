package com.ruralhealthcare.RHCW.service;

import com.ruralhealthcare.RHCW.dto.TeleconsultationDTO;
import com.ruralhealthcare.RHCW.exception.ResourceNotFoundException;
import com.ruralhealthcare.RHCW.model.Doctor;
import com.ruralhealthcare.RHCW.model.Patient;
import com.ruralhealthcare.RHCW.model.Teleconsultation;
import com.ruralhealthcare.RHCW.repository.DoctorRepository;
import com.ruralhealthcare.RHCW.repository.PatientRepository;
import com.ruralhealthcare.RHCW.repository.TeleconsultationRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class TeleConsultServiceImpl implements TeleconsultationService{

    private final TeleconsultationRepository teleRepo;
    private final PatientRepository patientRepo;
    private final DoctorRepository doctorRepo;

    public TeleConsultServiceImpl(TeleconsultationRepository teleRepo, PatientRepository patientRepo,
                                  DoctorRepository doctorRepo) {
        this.doctorRepo = doctorRepo;
        this.teleRepo = teleRepo;
        this.patientRepo = patientRepo;
    }
    @Override
    public Teleconsultation schedule(TeleconsultationDTO dto) {
        Patient patient = patientRepo.findById(dto.getPatientId())
                .orElseThrow(()->new ResourceNotFoundException("Patient not found"));

        Doctor doctor = doctorRepo.findById(dto.getDoctorId())
                .orElseThrow(()->new ResourceNotFoundException("Doctor not found"));

        LocalDateTime scheduledAt = LocalDateTime.parse(dto.getScheduledAt());

        String videoLink = "https://videocall.example.com/meet/" + UUID.randomUUID();

        Teleconsultation teleconsultation = Teleconsultation.builder()
                .patient(patient)
                .doctor(doctor)
                .scheduledAt(scheduledAt)
                .notes(dto.getNotes())
                .videoLink(videoLink)
                .status(Teleconsultation.TeleStatus.SCHEDULED)
                .build();
        return teleRepo.save(teleconsultation);
    }

    @Override
    public Teleconsultation get(Long id) {
        return teleRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TeleConsultation not found"));
    }

    @Override
    public List<Teleconsultation> getByPatient(Long patientId) {
        return teleRepo.findByPatientId(patientId);
    }

    @Override
    public Teleconsultation assignDoctorAndStart(Long id, Long doctorId) {
        Teleconsultation teleconsultation = get(id);
        Doctor doctor = doctorRepo.findById(doctorId)
                .orElseThrow(()->new ResourceNotFoundException("Doctor not found"));

        teleconsultation.setDoctor(doctor);
        teleconsultation.setStatus(Teleconsultation.TeleStatus.ONGOING);

        return teleRepo.save(teleconsultation);
    }

    @Override
    public Teleconsultation updateStatus(Long id, String status) {
        Teleconsultation teleconsultation = get(id);
        teleconsultation.setStatus(Teleconsultation.TeleStatus.valueOf(status));
        return teleRepo.save(teleconsultation);
    }
}
