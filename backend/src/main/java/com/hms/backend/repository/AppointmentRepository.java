package com.hms.backend.repository;

import com.hms.backend.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AppointmentRepository
        extends JpaRepository<Appointment, Long> {

    // Patient ki sari appointments
    List<Appointment> findByPatientId(Long patientId);

    // Doctor ki sari appointments
    List<Appointment> findByDoctorId(Long doctorId);

    // Status se appointments dhundna
    List<Appointment> findByStatus(String status);

    // Date se appointments dhundna
    List<Appointment> findByAppointmentDate(
            LocalDate date);

    // Doctor ki specific date ki appointments
    List<Appointment> findByDoctorIdAndAppointmentDate(
            Long doctorId, LocalDate date);

    // Patient ki specific status appointments
    List<Appointment> findByPatientIdAndStatus(
            Long patientId, String status);
}