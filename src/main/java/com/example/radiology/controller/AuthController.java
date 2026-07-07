package com.example.radiology.controller;

import com.example.radiology.entity.Utente;
import com.example.radiology.repository.UtenteRepository;
import com.example.radiology.security.JwtService;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;
import java.util.Set;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthController {

    private final UtenteRepository utenteRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Inject
    public AuthController(UtenteRepository utenteRepository,
                          PasswordEncoder passwordEncoder,
                          JwtService jwtService) {
        this.utenteRepository = utenteRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @POST
    @Path("/login")
    @PermitAll
    public Response login(Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");
        System.out.println(">>> STO USANDO UN VIRTUAL THREAD? " + Thread.currentThread().isVirtual());
        // 1. Cerca l'utente nel DB (ora restituisce direttamente l'entità o null)
        Utente utente = utenteRepository.findByUsername(username);

        // 2. Verifica se l'utente è attivo e se la password inserita coincide con l'hash BCrypt
        if (utente != null && utente.getAttivo() && passwordEncoder.matches(password, utente.getPassword().trim())) {

            Set<String> roles = java.util.Arrays.stream(utente.getRuoli().split(","))
                    .map(String::trim)
                    .collect(java.util.stream.Collectors.toSet());

            // Generiamo il vero JWT token firmato compatibile con Quarkus
            String realJwtToken = jwtService.generateToken(utente, roles);

            return Response.ok(Map.of(
                    "token", realJwtToken,
                    "roles", roles
            )).build();
        }

        // Se le credenziali o lo stato attivo non sono validi
        return Response.status(Response.Status.UNAUTHORIZED)
                .entity(Map.of("error", "Credenziali non valide"))
                .build();
    }

}