package com.hms.backend.repository;

import com.hms.backend.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository
        extends JpaRepository<Patient, Long> {

    // User ID se patient dhundna
    Optional<Patient> findByUserId(Long userId);

    // Gender se patients dhundna
    List<Patient> findByGender(String gender);

    // Blood Group se patients dhundna
    List<Patient> findByBloodGroup(String bloodGroup);

    // Patient exist karta hai ya nahi
    Boolean existsByUserId(Long userId);
}