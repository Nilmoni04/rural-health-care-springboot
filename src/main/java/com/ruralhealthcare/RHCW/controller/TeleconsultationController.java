package com.ruralhealthcare.RHCW.controller;

import com.ruralhealthcare.RHCW.dto.TeleconsultationDTO;
import com.ruralhealthcare.RHCW.model.Teleconsultation;
import com.ruralhealthcare.RHCW.service.TeleconsultationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teleconsults")
public class TeleconsultationController {
    private final TeleconsultationService service;

    public TeleconsultationController(TeleconsultationService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Teleconsultation> schedule(@Valid @RequestBody TeleconsultationDTO dto) {
        return ResponseEntity.ok(service.schedule(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Teleconsultation> get(@PathVariable Long id){
        return ResponseEntity.ok(service.get(id));
    }
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Teleconsultation>> getByPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(service.getByPatient(patientId));
    }
    @PutMapping("/{id}/assign")
    public ResponseEntity<Teleconsultation> assignDoctor(@PathVariable Long id, @RequestParam Long doctorId) {
        return ResponseEntity.ok(service.assignDoctorAndStart(id, doctorId));
    }
    @PutMapping("/{id}/status")
    public ResponseEntity<Teleconsultation> updateStatus(@PathVariable Long id, @RequestParam String status) {
        return ResponseEntity.ok(service.updateStatus(id, status));
    }
}
