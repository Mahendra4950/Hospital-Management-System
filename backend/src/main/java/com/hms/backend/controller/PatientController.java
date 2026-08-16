package com.hms.backend.controller;

import com.hms.backend.entity.Patient;
import com.hms.backend.entity.User;
import com.hms.backend.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/patients")
@CrossOrigin(origins = "*")
public class PatientController {

    @Autowired
    private PatientService patientService;

    // ─── Naya Patient Register karna ──────────────────
    @PostMapping("/register")
    public ResponseEntity<?> registerPatient(
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

            // Patient details nikalo
            Patient patient = new Patient();
            patient.setDateOfBirth(
                    LocalDate.parse(
                            (String) request
                                    .get("dateOfBirth")));
            patient.setGender(
                    (String) request.get("gender"));
            patient.setBloodGroup(
                    (String) request.get("bloodGroup"));
            patient.setAddress(
                    (String) request.get("address"));
            patient.setEmergencyContact(
                    (String) request
                            .get("emergencyContact"));
            patient.setMedicalHistory(
                    (String) request
                            .get("medicalHistory"));
            patient.setAllergies(
                    (String) request.get("allergies"));

            Patient saved = patientService
                    .registerPatient(patient, user);

            return ResponseEntity.ok(saved);

        } catch (RuntimeException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    // ─── Sare Patients Lana ───────────────────────────
    @GetMapping("/all")
    public ResponseEntity<List<Patient>>
    getAllPatients() {
        return ResponseEntity.ok(
                patientService.getAllPatients());
    }

    // ─── ID se Patient Lana ───────────────────────────
    @GetMapping("/{id}")
    public ResponseEntity<?> getPatientById(
            @PathVariable Long id) {
        return patientService.getPatientById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity
                        .notFound().build());
    }

    // ─── Blood Group se Patients Lana ─────────────────
    @GetMapping("/bloodgroup/{bloodGroup}")
    public ResponseEntity<List<Patient>>
    getByBloodGroup(
            @PathVariable String bloodGroup) {
        return ResponseEntity.ok(
                patientService
                        .getPatientsByBloodGroup(bloodGroup));
    }

    // ─── Gender se Patients Lana ──────────────────────
    @GetMapping("/gender/{gender}")
    public ResponseEntity<List<Patient>> getByGender(
            @PathVariable String gender) {
        return ResponseEntity.ok(
                patientService
                        .getPatientsByGender(gender));
    }

    // ─── Patient Update karna ─────────────────────────
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updatePatient(
            @PathVariable Long id,
            @RequestBody Patient patient) {
        try {
            Patient updated = patientService
                    .updatePatient(id, patient);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    // ─── Patient Delete karna ─────────────────────────
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePatient(
            @PathVariable Long id) {
        try {
            patientService.deletePatient(id);
            return ResponseEntity.ok(
                    "Patient deleted successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }
}