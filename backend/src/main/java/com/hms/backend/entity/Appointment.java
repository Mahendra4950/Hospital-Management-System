package com.hms.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Kaun sa Patient hai
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    // Kaun sa Doctor hai
    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    // Appointment ki date
    @Column(nullable = false)
    private LocalDate appointmentDate;

    // Appointment ka time
    @Column(nullable = false)
    private LocalTime appointmentTime;

    // Status — PENDING, CONFIRMED, CANCELLED, COMPLETED
    @Column(nullable = false, length = 20)
    private String status = "PENDING";

    // Reason for visit
    @Column(length = 255)
    private String reason;

    // Doctor ke notes
    @Column(length = 500)
    private String notes;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // ─── Getters & Setters ─────────────────────────────

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) {
        this.patient = patient; }

    public Doctor getDoctor() { return doctor; }
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor; }

    public LocalDate getAppointmentDate() {
        return appointmentDate; }
    public void setAppointmentDate(
            LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate; }

    public LocalTime getAppointmentTime() {
        return appointmentTime; }
    public void setAppointmentTime(
            LocalTime appointmentTime) {
        this.appointmentTime = appointmentTime; }

    public String getStatus() { return status; }
    public void setStatus(String status) {
        this.status = status; }

    public String getReason() { return reason; }
    public void setReason(String reason) {
        this.reason = reason; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) {
        this.notes = notes; }

    public LocalDateTime getCreatedAt() {
        return createdAt; }
    public LocalDateTime getUpdatedAt() {
        return updatedAt; }
}