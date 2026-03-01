package com.jspider.hospital_management_system_spring_boot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jspider.hospital_management_system_spring_boot.dao.PatientsDao;
import com.jspider.hospital_management_system_spring_boot.entity.Patient;

@Service
public class PatientService implements PatientsService {

    @Autowired
    private PatientsDao dao;

    @Override
    public Patient savePatient(Patient patient) {
        if (patient == null) return null;
        if (dao.existsByEmailDao(patient.getEmail())) return null;
        return dao.savePatientDao(patient);
    }

    @Override
    public List<Patient> getAllPatient() {
        return dao.getAllPatientDao();
    }

    @Override
    public Optional<Patient> getPatientById(int id) {
        return dao.getPatientByIdDao(id);
    }

    @Override
    public Patient findByContactNumber(String contactNumber) {
        if (contactNumber == null || contactNumber.isEmpty()) return null;
        return dao.findByContactNumberDao(contactNumber);
    }

    @Override
    public Patient findByEmail(String email) {
        if (email == null || email.isEmpty()) return null;
        if (dao.existsByEmailDao(email)) {
            return dao.findByEmailDao(email);
        }
        return null;
    }

    @Override
    public boolean existsByEmail(String email) {
        if (email == null || email.isEmpty()) return false;
        return dao.existsByEmailDao(email);
    }

    @Override
    public boolean authenticatePatientCredentials(int patientId, String password) {
        if (password == null || password.isEmpty()) return false;
        return dao.isValidPatient(patientId, password);
    }
}
