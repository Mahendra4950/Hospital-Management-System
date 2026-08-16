package com.hms.backend.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    // Secret key — 256 bit se bada hona chahiye
    private static final String SECRET =
            "hmsSecretKey2024hmsSecretKey2024hmsSecretKey2024";

    // Token 24 ghante tak valid rahega
    private static final long EXPIRATION =
            1000 * 60 * 60 * 24;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    // ─── Token Generate karo ───────────────────────────
    public String generateToken(String email, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis()
                                + EXPIRATION))
                .signWith(getSigningKey(),
                        SignatureAlgorithm.HS256)
                .compact();
    }

    // ─── Token se Email nikalo ─────────────────────────
    public String extractEmail(String token) {
        return extractClaims(token).getSubject();
    }

    // ─── Token se Role nikalo ──────────────────────────
    public String extractRole(String token) {
        return extractClaims(token)
                .get("role", String.class);
    }

    // ─── Token Valid hai ya nahi ───────────────────────
    public Boolean validateToken(String token,
                                 String email) {
        String extractedEmail = extractEmail(token);
        return extractedEmail.equals(email)
                && !isTokenExpired(token);
    }

    // ─── Token Expire hua ya nahi ─────────────────────
    private Boolean isTokenExpired(String token) {
        return extractClaims(token)
                .getExpiration()
                .before(new Date());
    }

    // ─── Claims nikalo ─────────────────────────────────
    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
