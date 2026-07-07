package com.example.radiology.repository;

import com.example.radiology.entity.Contenitore;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ContenitoreRepository implements PanacheRepository<Contenitore> {
}
