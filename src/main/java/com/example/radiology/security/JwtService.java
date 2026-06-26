package com.example.radiology.security;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Set;

@ApplicationScoped
public class JwtService {

    public String generateToken(String username, Set<String> roles) {
        return Jwt.issuer("http://localhost:8080")
                .upn(username)
                .subject(username)
                .groups(roles)
                .expiresIn(86400)
                .sign("privateKey.pem");
    }

}