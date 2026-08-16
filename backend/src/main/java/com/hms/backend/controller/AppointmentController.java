package com.hms.backend.controller;

import com.hms.backend.entity.Appointment;
import com.hms.backend.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/appointments")
@CrossOrigin(origins = "*")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    // ─── Naya Appointment Book karna ──────────────────
    @PostMapping("/book")
    public ResponseEntity<?> bookAppointment(
            @RequestBody Map<String, Object> request) {
        try {
            Long patientId = Long.valueOf(
                    request.get("patientId").toString());
            Long doctorId = Long.valueOf(
                    request.get("doctorId").toString());

            Appointment appointment = new Appointment();
            appointment.setAppointmentDate(
                    LocalDate.parse(
                            (String) request
                                    .get("appointmentDate")));
            appointment.setAppointmentTime(
                    LocalTime.parse(
                            (String) request
                                    .get("appointmentTime")));
            appointment.setReason(
                    (String) request.get("reason"));

            Appointment saved = appointmentService
                    .bookAppointment(
                            patientId, doctorId, appointment);

            return ResponseEntity.ok(saved);

        } catch (RuntimeException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    // ─── Sari Appointments Lana ───────────────────────
    @GetMapping("/all")
    public ResponseEntity<List<Appointment>>
    getAllAppointments() {
        return ResponseEntity.ok(
                appointmentService.getAllAppointments());
    }

    // ─── ID se Appointment Lana ───────────────────────
    @GetMapping("/{id}")
    public ResponseEntity<?> getAppointmentById(
            @PathVariable Long id) {
        return appointmentService
                .getAppointmentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity
                        .notFound().build());
    }

    // ─── Patient ki Appointments ──────────────────────
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Appointment>>
    getByPatient(
            @PathVariable Long patientId) {
        return ResponseEntity.ok(
                appointmentService
                        .getAppointmentsByPatient(patientId));
    }

    // ─── Doctor ki Appointments ───────────────────────
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<Appointment>>
    getByDoctor(
            @PathVariable Long doctorId) {
        return ResponseEntity.ok(
                appointmentService
                        .getAppointmentsByDoctor(doctorId));
    }

    // ─── Status se Appointments ───────────────────────
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Appointment>>
    getByStatus(
            @PathVariable String status) {
        return ResponseEntity.ok(
                appointmentService
                        .getAppointmentsByStatus(status));
    }

    // ─── Date se Appointments ─────────────────────────
    @GetMapping("/date/{date}")
    public ResponseEntity<List<Appointment>>
    getByDate(
            @PathVariable String date) {
        return ResponseEntity.ok(
                appointmentService
                        .getAppointmentsByDate(
                                LocalDate.parse(date)));
    }

    // ─── Doctor ka Schedule ───────────────────────────
    @GetMapping("/schedule/{doctorId}/{date}")
    public ResponseEntity<List<Appointment>>
    getDoctorSchedule(
            @PathVariable Long doctorId,
            @PathVariable String date) {
        return ResponseEntity.ok(
                appointmentService.getDoctorSchedule(
                        doctorId, LocalDate.parse(date)));
    }

    // ─── Appointment Status Update ────────────────────
    @PutMapping("/status/{id}")
    public ResponseEntity<?> updateStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {
        try {
            Appointment updated = appointmentService
                    .updateStatus(
                            id, request.get("status"));
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    // ─── Doctor Notes Add karna ───────────────────────
    @PutMapping("/notes/{id}")
    public ResponseEntity<?> addNotes(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {
        try {
            Appointment updated = appointmentService
                    .addNotes(id, request.get("notes"));
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    // ─── Appointment Cancel karna ─────────────────────
    @PutMapping("/cancel/{id}")
    public ResponseEntity<?> cancelAppointment(
            @PathVariable Long id) {
        try {
            Appointment cancelled = appointmentService
                    .cancelAppointment(id);
            return ResponseEntity.ok(cancelled);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }
}