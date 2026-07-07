package com.example.radiology.repository;

import com.example.radiology.entity.Organizzazione;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OrganizzazioneRepository implements PanacheRepository<Organizzazione> {
}