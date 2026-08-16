package com.hms.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok(
                "Admin API is Working!");
    }
}