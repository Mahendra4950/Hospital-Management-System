package com.hms.backend.service;

import com.hms.backend.entity.Doctor;
import com.hms.backend.entity.User;
import com.hms.backend.enums.Role;
import com.hms.backend.repository.DoctorRepository;
import com.hms.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ─── Naya Doctor Register karna ───────────────────
    public Doctor registerDoctor(Doctor doctor,
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

        // Role DOCTOR set karo
        user.setRole(Role.DOCTOR);

        // Pehle User save karo
        User savedUser = userRepository.save(user);

        // Doctor ko User se link karo
        doctor.setUser(savedUser);

        // Doctor save karo
        return doctorRepository.save(doctor);
    }

    // ─── Sare Doctors Lana ────────────────────────────
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    // ─── ID se Doctor Dhundna ─────────────────────────
    public Optional<Doctor> getDoctorById(Long id) {
        return doctorRepository.findById(id);
    }

    // ─── Specialization se Doctors Dhundna ───────────
    public List<Doctor> getDoctorsBySpecialization(
            String specialization) {
        return doctorRepository
                .findBySpecialization(specialization);
    }

    // ─── Department se Doctors Dhundna ───────────────
    public List<Doctor> getDoctorsByDepartment(
            String department) {
        return doctorRepository
                .findByDepartment(department);
    }

    // ─── Available Doctors Dhundna ────────────────────
    public List<Doctor> getAvailableDoctors() {
        return doctorRepository
                .findByIsAvailable(true);
    }

    // ─── Doctor Update karna ──────────────────────────
    public Doctor updateDoctor(Long id,
                               Doctor updatedDoctor) {
        Doctor existing = doctorRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Doctor not found!"));

        existing.setSpecialization(
                updatedDoctor.getSpecialization());
        existing.setQualification(
                updatedDoctor.getQualification());
        existing.setExperienceYears(
                updatedDoctor.getExperienceYears());
        existing.setConsultationFee(
                updatedDoctor.getConsultationFee());
        existing.setDepartment(
                updatedDoctor.getDepartment());
        existing.setPhone(updatedDoctor.getPhone());

        return doctorRepository.save(existing);
    }

    // ─── Doctor Available/Unavailable karna ──────────
    public Doctor toggleAvailability(Long id) {
        Doctor doctor = doctorRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Doctor not found!"));

        doctor.setIsAvailable(
                !doctor.getIsAvailable());
        return doctorRepository.save(doctor);
    }

    // ─── Doctor Delete karna ──────────────────────────
    public void deleteDoctor(Long id) {
        Doctor doctor = doctorRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Doctor not found!"));

        doctorRepository.delete(doctor);
    }
}