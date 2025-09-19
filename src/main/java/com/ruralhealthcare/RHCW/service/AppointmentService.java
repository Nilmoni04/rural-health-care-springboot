package com.ruralhealthcare.RHCW.service;

import com.ruralhealthcare.RHCW.dto.AppointmentDTO;
import com.ruralhealthcare.RHCW.model.Appointment;

import java.util.List;

public interface AppointmentService {
    Appointment bookAppointment(AppointmentDTO dto);
    Appointment getAppointment(Long id);
    List<Appointment> getAppointmentByPatient(Long patientId);
    Appointment updateStatus(Long id, String status);

}
