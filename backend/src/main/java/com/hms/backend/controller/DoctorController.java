package com.hms.backend.controller;

import com.hms.backend.entity.Doctor;
import com.hms.backend.entity.User;
import com.hms.backend.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/doctors")
@CrossOrigin(origins = "*")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    // ─── Naya Doctor Register karna ───────────────────
    @PostMapping("/register")
    public ResponseEntity<?> registerDoctor(
            @RequestBody Map<String, Object> request) {
        try {
            // User details nikalo
            User user = new User();
            user.setFirstName(
                    (String) request.get("firstName"));
            user.setLastName(
                    (String) request.get("lastName"));
            user.setEmail(
                    (String) request.get("email"));
            user.setPassword(
                    (String) request.get("password"));
            user.setPhone(
                    (String) request.get("phone"));

            // Doctor details nikalo
            Doctor doctor = new Doctor();
            doctor.setSpecialization(
                    (String) request.get("specialization"));
            doctor.setQualification(
                    (String) request.get("qualification"));
            doctor.setExperienceYears(
                    (Integer) request.get("experienceYears"));
            doctor.setConsultationFee(
                    ((Number) request
                            .get("consultationFee"))
                            .doubleValue());
            doctor.setDepartment(
                    (String) request.get("department"));
            doctor.setPhone(
                    (String) request.get("phone"));

            Doctor saved = doctorService
                    .registerDoctor(doctor, user);

            return ResponseEntity.ok(saved);

        } catch (RuntimeException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    // ─── Sare Doctors Lana ────────────────────────────
    @GetMapping("/all")
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        return ResponseEntity.ok(
                doctorService.getAllDoctors());
    }

    // ─── ID se Doctor Lana ────────────────────────────
    @GetMapping("/{id}")
    public ResponseEntity<?> getDoctorById(
            @PathVariable Long id) {
        return doctorService.getDoctorById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity
                        .notFound().build());
    }

    // ─── Specialization se Doctors Lana ──────────────
    @GetMapping("/specialization/{spec}")
    public ResponseEntity<List<Doctor>> getBySpec(
            @PathVariable String spec) {
        return ResponseEntity.ok(
                doctorService
                        .getDoctorsBySpecialization(spec));
    }

    // ─── Available Doctors Lana ───────────────────────
    @GetMapping("/available")
    public ResponseEntity<List<Doctor>>
    getAvailableDoctors() {
        return ResponseEntity.ok(
                doctorService.getAvailableDoctors());
    }

    // ─── Doctor Update karna ──────────────────────────
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateDoctor(
            @PathVariable Long id,
            @RequestBody Doctor doctor) {
        try {
            Doctor updated = doctorService
                    .updateDoctor(id, doctor);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    // ─── Doctor Availability Toggle ───────────────────
    @PutMapping("/toggle/{id}")
    public ResponseEntity<?> toggleAvailability(
            @PathVariable Long id) {
        try {
            Doctor updated = doctorService
                    .toggleAvailability(id);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    // ─── Doctor Delete karna ──────────────────────────
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteDoctor(
            @PathVariable Long id) {
        try {
            doctorService.deleteDoctor(id);
            return ResponseEntity.ok(
                    "Doctor deleted successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }
}