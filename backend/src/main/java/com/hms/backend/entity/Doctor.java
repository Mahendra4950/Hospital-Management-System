package com.hms.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "doctors")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Doctor ka User account
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Doctor ki specialization
    @Column(nullable = false, length = 100)
    private String specialization;

    // Qualification — MBBS, MD etc
    @Column(nullable = false, length = 100)
    private String qualification;

    // Experience years
    @Column(nullable = false)
    private Integer experienceYears;

    // Consultation fees
    @Column(nullable = false)
    private Double consultationFee;

    // Available hai ya nahi
    @Column(nullable = false)
    private Boolean isAvailable = true;

    // Phone number
    @Column(length = 15)
    private String phone;

    // Department
    @Column(length = 100)
    private String department;

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

    public String getSpecialization() {
        return specialization; }
    public void setSpecialization(String specialization) {
        this.specialization = specialization; }

    public String getQualification() {
        return qualification; }
    public void setQualification(String qualification) {
        this.qualification = qualification; }

    public Integer getExperienceYears() {
        return experienceYears; }
    public void setExperienceYears(Integer experienceYears) {
        this.experienceYears = experienceYears; }

    public Double getConsultationFee() {
        return consultationFee; }
    public void setConsultationFee(Double consultationFee) {
        this.consultationFee = consultationFee; }

    public Boolean getIsAvailable() { return isAvailable; }
    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) {
        this.department = department; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}