package com.ruralhealthcare.RHCW.controller;

import com.ruralhealthcare.RHCW.dto.AppointmentDTO;
import com.ruralhealthcare.RHCW.model.Appointment;
import com.ruralhealthcare.RHCW.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {
    private final AppointmentService service;
    public AppointmentController(AppointmentService service) {
        this.service=service;
    }
    @PostMapping
    public ResponseEntity<Appointment> book(@Valid @RequestBody AppointmentDTO dto) {
        Appointment ap = service.bookAppointment(dto);
        return ResponseEntity.ok(ap);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Appointment> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.getAppointment(id));
    }
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Appointment>> getByPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(service.getAppointmentByPatient(patientId));
    }
    @PutMapping("/{id}/status")
    public ResponseEntity<Appointment> updateStatus(@PathVariable Long id, @RequestParam String status) {
        return ResponseEntity.ok(service.updateStatus(id, status));
    }
}
