package com.ruralhealthcare.RHCW.controller;

import com.ruralhealthcare.RHCW.dto.AmbulanceRequestDTO;
import com.ruralhealthcare.RHCW.model.AmbulanceRequest;
import com.ruralhealthcare.RHCW.service.AmbulanceService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ambulances")
public class AmbulanceController {
    private final AmbulanceService service;

    public AmbulanceController(AmbulanceService service) {
        this.service=service;
    }

    @PostMapping
    public ResponseEntity<AmbulanceRequest> request(@Valid @RequestBody AmbulanceRequestDTO dto) {
        return ResponseEntity.ok(service.requestAmbulance(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AmbulanceRequest> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.getRequest(id));
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<AmbulanceRequest>> getByPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(service.getByPatient(patientId));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<AmbulanceRequest> updateStatus(@PathVariable Long id,
                                                         @RequestParam String status,
                                                         @RequestParam(required = false) String assignedAmbulanceId) {
        return ResponseEntity.ok(service.updateStatus(id, status, assignedAmbulanceId));
    }
}
