package com.hms.backend.repository;

import com.hms.backend.entity.User;
import com.hms.backend.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Email se user dhundna — Login ke liye
    Optional<User> findByEmail(String email);

    // Email already exist karta hai ya nahi — Registration ke liye
    Boolean existsByEmail(String email);

    // Phone already exist karta hai ya nahi
    Boolean existsByPhone(String phone);

    // Role ke hisaab se sare users lana — Admin ke liye
    List<User> findByRole(Role role);

    // Sirf active users lana
    List<User> findByIsActive(Boolean isActive);
}