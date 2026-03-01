package com.jspider.hospital_management_system_spring_boot.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jspider.hospital_management_system_spring_boot.entity.Doctor;
import com.jspider.hospital_management_system_spring_boot.entity.DoctorNote;
import com.jspider.hospital_management_system_spring_boot.entity.Patient;
import com.jspider.hospital_management_system_spring_boot.reposetory.DoctorNoteRepository;

@Service
public class DoctorNoteService {

    @Autowired
    private DoctorNoteRepository doctorNoteRepository;

    @Autowired
    private DoctorsService doctorsService;

    @Autowired
    private PatientsService patientsService;

    public DoctorNote saveDoctorNote(DoctorNote doctorNote) {
        if (doctorNote.getDoctor() == null || doctorNote.getDoctor().getDoctorId() == null) {
            throw new IllegalArgumentException("Doctor information must be provided!");
        }
        if (doctorNote.getPatient() == null) {
            throw new IllegalArgumentException("Patient information must be provided!");
        }

        Optional<Doctor> doctorOpt = doctorsService.getDoctorById(doctorNote.getDoctor().getDoctorId());
        if (!doctorOpt.isPresent()) {
            throw new RuntimeException("Doctor not found with ID: " + doctorNote.getDoctor().getDoctorId());
        }
        doctorNote.setDoctor(doctorOpt.get());

        Optional<Patient> patientOpt = patientsService.getPatientById(doctorNote.getPatient().getPatientId());
        if (!patientOpt.isPresent()) {
            throw new RuntimeException("Patient not found with ID: " + doctorNote.getPatient().getPatientId());
        }
        doctorNote.setPatient(patientOpt.get());

        return doctorNoteRepository.save(doctorNote);
    }

    public List<DoctorNote> getNotesByPatientId(Integer patientId) {
        if (patientId == null) return Collections.emptyList();
        Optional<Patient> patientOpt = patientsService.getPatientById(patientId);
        return patientOpt.map(doctorNoteRepository::findByPatient).orElse(Collections.emptyList());
    }
}
