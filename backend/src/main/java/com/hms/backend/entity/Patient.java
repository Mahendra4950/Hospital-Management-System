package com.hms.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Patient ka User account
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Date of Birth
    @Column(nullable = false)
    private LocalDate dateOfBirth;

    // Gender
    @Column(nullable = false, length = 10)
    private String gender;

    // Blood Group
    @Column(length = 5)
    private String bloodGroup;

    // Address
    @Column(length = 255)
    private String address;

    // Emergency Contact
    @Column(length = 15)
    private String emergencyContact;

    // Medical History
    @Column(length = 500)
    private String medicalHistory;

    // Allergies
    @Column(length = 255)
    private String allergies;

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

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public LocalDate getDateOfBirth() {
        return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth; }

    public String getGender() { return gender; }
    public void setGender(String gender) {
        this.gender = gender; }

    public String getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup; }

    public String getAddress() { return address; }
    public void setAddress(String address) {
        this.address = address; }

    public String getEmergencyContact() {
        return emergencyContact; }
    public void setEmergencyContact(
            String emergencyContact) {
        this.emergencyContact = emergencyContact; }

    public String getMedicalHistory() {
        return medicalHistory; }
    public void setMedicalHistory(
            String medicalHistory) {
        this.medicalHistory = medicalHistory; }

    public String getAllergies() { return allergies; }
    public void setAllergies(String allergies) {
        this.allergies = allergies; }

    public LocalDateTime getCreatedAt() {
        return createdAt; }
    public LocalDateTime getUpdatedAt() {
        return updatedAt; }
}