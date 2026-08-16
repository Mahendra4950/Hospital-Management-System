package com.hms.backend.repository;

import com.hms.backend.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository
        extends JpaRepository<Doctor, Long> {

    // User ID se doctor dhundna
    Optional<Doctor> findByUserId(Long userId);

    // Specialization se doctors dhundna
    List<Doctor> findBySpecialization(
            String specialization);

    // Available doctors dhundna
    List<Doctor> findByIsAvailable(Boolean isAvailable);

    // Department se doctors dhundna
    List<Doctor> findByDepartment(String department);

    // Doctor exist karta hai ya nahi user ID se
    Boolean existsByUserId(Long userId);
}