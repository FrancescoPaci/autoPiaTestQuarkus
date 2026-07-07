package com.example.radiology.repository;

import com.example.radiology.entity.Apparecchiatura;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ApparecchiaturaRepository implements PanacheRepository<Apparecchiatura> {
}