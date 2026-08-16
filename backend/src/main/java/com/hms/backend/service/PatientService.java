package com.hms.backend.service;

import com.hms.backend.entity.Patient;
import com.hms.backend.entity.User;
import com.hms.backend.enums.Role;
import com.hms.backend.repository.PatientRepository;
import com.hms.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ─── Naya Patient Register karna ──────────────────
    public Patient registerPatient(Patient patient,
                                   User user) {

        // Email already exist karta hai?
        if (userRepository.existsByEmail(
                user.getEmail())) {
            throw new RuntimeException(
                    "Email already registered!");
        }

        // Password encrypt karo
        user.setPassword(
                passwordEncoder.encode(user.getPassword()));

        // Role PATIENT set karo
        user.setRole(Role.PATIENT);

        // Pehle User save karo
        User savedUser = userRepository.save(user);

        // Patient ko User se link karo
        patient.setUser(savedUser);

        // Patient save karo
        return patientRepository.save(patient);
    }

    // ─── Sare Patients Lana ───────────────────────────
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    // ─── ID se Patient Dhundna ────────────────────────
    public Optional<Patient> getPatientById(Long id) {
        return patientRepository.findById(id);
    }

    // ─── User ID se Patient Dhundna ───────────────────
    public Optional<Patient> getPatientByUserId(
            Long userId) {
        return patientRepository
                .findByUserId(userId);
    }

    // ─── Blood Group se Patients Dhundna ─────────────
    public List<Patient> getPatientsByBloodGroup(
            String bloodGroup) {
        return patientRepository
                .findByBloodGroup(bloodGroup);
    }

    // ─── Gender se Patients Dhundna ───────────────────
    public List<Patient> getPatientsByGender(
            String gender) {
        return patientRepository
                .findByGender(gender);
    }

    // ─── Patient Update karna ─────────────────────────
    public Patient updatePatient(Long id,
                                 Patient updatedPatient) {
        Patient existing = patientRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Patient not found!"));

        existing.setDateOfBirth(
                updatedPatient.getDateOfBirth());
        existing.setGender(
                updatedPatient.getGender());
        existing.setBloodGroup(
                updatedPatient.getBloodGroup());
        existing.setAddress(
                updatedPatient.getAddress());
        existing.setEmergencyContact(
                updatedPatient.getEmergencyContact());
        existing.setMedicalHistory(
                updatedPatient.getMedicalHistory());
        existing.setAllergies(
                updatedPatient.getAllergies());

        return patientRepository.save(existing);
    }

    // ─── Patient Delete karna ─────────────────────────
    public void deletePatient(Long id) {
        Patient patient = patientRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Patient not found!"));

        patientRepository.delete(patient);
    }
}