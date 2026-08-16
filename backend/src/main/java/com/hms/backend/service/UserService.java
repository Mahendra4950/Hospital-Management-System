package com.hms.backend.service;
import com.hms.backend.config.JwtUtil;

import com.hms.backend.entity.User;
import com.hms.backend.enums.Role;
import com.hms.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;



    // ─── New User Register karna ───────────────────────
    public User registerUser(User user) {

        // Email already exist karta hai?
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already registered!");
        }

        // Phone already exist karta hai?
        if (user.getPhone() != null &&
                userRepository.existsByPhone(user.getPhone())) {
            throw new RuntimeException("Phone number already registered!");
        }

        // Password ko BCrypt se encrypt karo
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Default role set karo agar role nahi diya
        if (user.getRole() == null) {
            user.setRole(Role.PATIENT);
        }

        // User save karo
        return userRepository.save(user);
    }

    // ─── Email se User Dhundna ─────────────────────────
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // ─── ID se User Dhundna ────────────────────────────
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    // ─── Role wise Users Lana ──────────────────────────
    public List<User> getUsersByRole(Role role) {
        return userRepository.findByRole(role);
    }

    // ─── Sare Users Lana ───────────────────────────────
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // ─── User Active/Inactive karna ────────────────────
    public User toggleUserStatus(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found!"));

        user.setIsActive(!user.getIsActive());
        return userRepository.save(user);
    }

    // ─── User Update karna ─────────────────────────────
    public User updateUser(Long id, User updatedUser) {
        User existing = userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found!"));

        existing.setFirstName(updatedUser.getFirstName());
        existing.setLastName(updatedUser.getLastName());
        existing.setPhone(updatedUser.getPhone());
        existing.setRole(updatedUser.getRole());

        return userRepository.save(existing);
    }
}