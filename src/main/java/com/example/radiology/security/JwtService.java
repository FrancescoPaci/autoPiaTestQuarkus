package com.example.radiology.security;

import com.example.radiology.entity.Utente;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Set;

@ApplicationScoped
public class JwtService {

    public String generateToken(Utente utente) {

        Set<String> roles = java.util.Arrays.stream(utente.getRuoli().split(","))
                .map(String::trim)
                .collect(java.util.stream.Collectors.toSet());

        return Jwt.issuer("http://localhost:8080")
                .upn(utente.getUsername())
                .subject(utente.getUsername())
                .groups(roles)
                .claim("azienda", utente.getAzienda())
                .expiresIn(86400)
                .sign("privateKey.pem");
    }

}