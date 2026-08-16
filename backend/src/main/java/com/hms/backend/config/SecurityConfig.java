package com.hms.backend.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Lazy
    @Autowired
    private JwtFilter jwtFilter;

    // ─── CORS Configuration ────────────────────────
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config =
                new CorsConfiguration();

        // React frontend allow karo
        config.setAllowedOrigins(
                List.of("http://localhost:3000"));

        // Sare methods allow karo
        config.setAllowedMethods(
                Arrays.asList(
                        "GET", "POST", "PUT",
                        "DELETE", "OPTIONS"));

        // Sare headers allow karo
        config.setAllowedHeaders(
                List.of("*"));

        // Credentials allow karo
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(
                "/**", config);

        return source;
    }

    // ─── Security Filter Chain ─────────────────────
    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors
                        .configurationSource(
                                corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/auth/**"
                        ).permitAll()
                        // Patient bhi ye APIs access kar sake
                        .requestMatchers(
                                "/api/admin/doctors/available",
                                "/api/admin/patients/all"
                        ).hasAnyRole("ADMIN", "PATIENT")
                        // Sirf Admin
                        .requestMatchers(
                                "/api/admin/**"
                        ).hasRole("ADMIN")
                        // Sirf Doctor
                        .requestMatchers(
                                "/api/doctor/**"
                        ).hasRole("DOCTOR")
                        // Sirf Patient
                        .requestMatchers(
                                "/api/patient/**"
                        ).hasRole("PATIENT")
                        // Appointments — sab access kar sake
                        .requestMatchers(
                                "/api/appointments/**"
                        ).hasAnyRole(
                                "ADMIN", "DOCTOR", "PATIENT")
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(
                                (request, response, e) ->
                                        response.sendError(
                                                HttpServletResponse
                                                        .SC_UNAUTHORIZED,
                                                "Unauthorized")))
                .addFilterBefore(
                        jwtFilter,
                        UsernamePasswordAuthenticationFilter
                                .class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}