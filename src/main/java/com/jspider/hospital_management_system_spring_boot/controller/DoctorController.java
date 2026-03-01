package com.jspider.hospital_management_system_spring_boot.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jspider.hospital_management_system_spring_boot.entity.Doctor;
import com.jspider.hospital_management_system_spring_boot.service.DoctorsService;

@RestController
@RequestMapping(value = "/api/doctor")
@CrossOrigin(value = "http://localhost:5173")
public class DoctorController {

    @Autowired
    private DoctorsService service;

    @PostMapping
    public ResponseEntity<?> saveDoctorController(@RequestBody Doctor doctor) {
        Doctor saved = service.saveDoctor(doctor);
        if (saved == null) {
            return ResponseEntity.badRequest().body("Doctor with this email or contact already exists.");
        }
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public List<Doctor> getAllDoctorController() {
        return service.getAllDoctor();
    }

    @GetMapping(value = "/id")
    public Optional<Doctor> getDoctorByIdController(@RequestParam long id) {
        return service.getDoctorById(id);
    }

    @GetMapping(value = "/email")
    public Doctor findByEmailController(@RequestParam String email) {
        return service.findByEmail(email);
    }

    @GetMapping(value = "/contactNumber")
    public Doctor findByContactNumber(@RequestParam String contactNumber) {
        return service.findByContactNumber(contactNumber);
    }

    @GetMapping(value = "/specialization")
    public List<Doctor> findBySpecializationController(@RequestParam String specialization) {
        return service.findBySpecialization(specialization);
    }

    @PostMapping(value = "/existsByEmail")
    public boolean existsByEmailController(@RequestParam String email) {
        return service.existsByEmail(email);
    }

    @PostMapping(value = "/existsByContactNumber")
    public boolean existsByContactNumberController(@RequestParam String contactNumber) {
        return service.existsByContactNumber(contactNumber);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Boolean> authenticateDoctor(@RequestParam Long doctorId,
            @RequestParam String password) {
        boolean isAuthenticated = service.authenticateDoctor(doctorId, password);
        return ResponseEntity.ok(isAuthenticated);
    }

    @DeleteMapping(value = "/delete/{id}")
    public boolean deleteDrByIdController(@PathVariable long id) {
        return service.deleteDrById(id);
    }
}
