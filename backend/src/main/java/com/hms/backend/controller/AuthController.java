package com.hms.backend.controller;

import com.hms.backend.entity.User;
import com.hms.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hms.backend.config.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ─── Register API ──────────────────────────────────
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            User savedUser = userService.registerUser(user);
            savedUser.setPassword(null); // Password response mein mat bhejo
            return ResponseEntity.ok(savedUser);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }


    // ─── Login API ─────────────────────────────────────
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            // Email se user dhundo
            User existing = userService
                    .findByEmail(user.getEmail())
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Email not found!"));

            // Password match karo
            if (!passwordEncoder.matches(
                    user.getPassword(),
                    existing.getPassword())) {
                return ResponseEntity
                        .badRequest()
                        .body("Invalid password!");
            }

            // User active hai ya nahi
            if (!existing.getIsActive()) {
                return ResponseEntity
                        .badRequest()
                        .body("Account is deactivated!");
            }

            // JWT Token generate karo
            String token = jwtUtil.generateToken(
                    existing.getEmail(),
                    existing.getRole().toString()
            );

            // Response banao
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("role", existing.getRole());
            response.put("email", existing.getEmail());
            response.put("firstName", existing.getFirstName());
            response.put("lastName", existing.getLastName());
            response.put("id", existing.getId());

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    // ─── Test API — Check karne ke liye ────────────────
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("HMS Backend is Running!");
    }
}