package com.hms.backend.service;

import com.hms.backend.entity.Appointment;
import com.hms.backend.entity.Doctor;
import com.hms.backend.entity.Patient;
import com.hms.backend.repository.AppointmentRepository;
import com.hms.backend.repository.DoctorRepository;
import com.hms.backend.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    // ─── Naya Appointment Book karna ──────────────────
    public Appointment bookAppointment(
            Long patientId,
            Long doctorId,
            Appointment appointment) {

        // Patient exist karta hai?
        Patient patient = patientRepository
                .findById(patientId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Patient not found!"));

        // Doctor exist karta hai?
        Doctor doctor = doctorRepository
                .findById(doctorId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Doctor not found!"));

        // Doctor available hai?
        if (!doctor.getIsAvailable()) {
            throw new RuntimeException(
                    "Doctor is not available!");
        }

        // Appointment set karo
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setStatus("PENDING");

        return appointmentRepository
                .save(appointment);
    }

    // ─── Sari Appointments Lana ───────────────────────
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    // ─── ID se Appointment Lana ───────────────────────
    public Optional<Appointment> getAppointmentById(
            Long id) {
        return appointmentRepository.findById(id);
    }

    // ─── Patient ki Appointments ──────────────────────
    public List<Appointment> getAppointmentsByPatient(
            Long patientId) {
        return appointmentRepository
                .findByPatientId(patientId);
    }

    // ─── Doctor ki Appointments ───────────────────────
    public List<Appointment> getAppointmentsByDoctor(
            Long doctorId) {
        return appointmentRepository
                .findByDoctorId(doctorId);
    }

    // ─── Status se Appointments ───────────────────────
    public List<Appointment> getAppointmentsByStatus(
            String status) {
        return appointmentRepository
                .findByStatus(status);
    }

    // ─── Date se Appointments ─────────────────────────
    public List<Appointment> getAppointmentsByDate(
            LocalDate date) {
        return appointmentRepository
                .findByAppointmentDate(date);
    }

    // ─── Doctor ka Schedule ───────────────────────────
    public List<Appointment> getDoctorSchedule(
            Long doctorId, LocalDate date) {
        return appointmentRepository
                .findByDoctorIdAndAppointmentDate(
                        doctorId, date);
    }

    // ─── Appointment Status Update karna ──────────────
    public Appointment updateStatus(
            Long id, String status) {
        Appointment appointment = appointmentRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Appointment not found!"));

        appointment.setStatus(status);
        return appointmentRepository.save(appointment);
    }

    // ─── Doctor Notes Add karna ───────────────────────
    public Appointment addNotes(Long id, String notes) {
        Appointment appointment = appointmentRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Appointment not found!"));

        appointment.setNotes(notes);
        appointment.setStatus("COMPLETED");
        return appointmentRepository.save(appointment);
    }

    // ─── Appointment Cancel karna ─────────────────────
    public Appointment cancelAppointment(Long id) {
        Appointment appointment = appointmentRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Appointment not found!"));

        appointment.setStatus("CANCELLED");
        return appointmentRepository.save(appointment);
    }
}