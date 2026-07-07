package com.example.radiology.repository;

import com.example.radiology.entity.Utente;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UtenteRepository implements PanacheRepository<Utente> {

    public Utente findByUsername(String username) {
        return find("username", username).firstResult();
    }

}