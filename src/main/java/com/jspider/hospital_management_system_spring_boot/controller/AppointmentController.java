package com.jspider.hospital_management_system_spring_boot.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jspider.hospital_management_system_spring_boot.entity.Appointment;
import com.jspider.hospital_management_system_spring_boot.service.AppointmentsService;

@RestController
@RequestMapping("/api/appointment")
@CrossOrigin(value = "http://localhost:5173")
public class AppointmentController {

    @Autowired
    private AppointmentsService service;

    @PostMapping
    public ResponseEntity<?> createAppointment(@RequestBody Appointment appointment) {
        if (appointment.getDoctor() == null || appointment.getPatient() == null) {
            return ResponseEntity.badRequest().body("Doctor and Patient must be provided.");
        }
        if (service.isDoctorBooked(appointment.getDoctor(), appointment.getAppointmentDate())) {
            return ResponseEntity.badRequest().body("Doctor is already booked on this date.");
        }
        if (service.isPatientBooked(appointment.getPatient(), appointment.getAppointmentDate())) {
            return ResponseEntity.badRequest().body("Patient already has an appointment on this date.");
        }
        Appointment saved = service.saveAppointment(appointment);
        if (saved == null) {
            return ResponseEntity.badRequest().body("Invalid Doctor ID or Patient ID.");
        }
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public List<Appointment> getAllAppointments() {
        return service.getAllAppointments();
    }

    @GetMapping(value = "/date")
    public List<Appointment> getAppointmentsByDate(@RequestParam String date) {
        return service.getAppointmentsByDate(LocalDate.parse(date));
    }

    @PutMapping("/{appointmentId}")
    public Appointment updateAppointment(@PathVariable int appointmentId,
            @RequestBody Appointment updatedAppointment) {
        return service.updateAppointment(appointmentId, updatedAppointment);
    }

    @GetMapping(value = "/patientId/{patientId}")
    public List<Appointment> getAppointmentsByPatientId(@PathVariable int patientId) {
        return service.getAppointmentsByPatientId(patientId);
    }

    @GetMapping(value = "/doctorId/{doctorId}")
    public List<Appointment> getAppointmentsByDoctorId(@PathVariable long doctorId) {
        return service.getAppointmentsByDoctorId((int) doctorId);
    }
}
