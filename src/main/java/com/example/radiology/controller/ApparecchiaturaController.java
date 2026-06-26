package com.example.radiology.controller;

import com.example.radiology.entity.Apparecchiatura;
import com.example.radiology.entity.Organizzazione;
import com.example.radiology.repository.ApparecchiaturaRepository;
import com.example.radiology.repository.OrganizzazioneRepository;
import com.example.radiology.security.VerificaAzienda;
import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
import java.util.Map;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@VerificaAzienda("Azienda A")
public class ApparecchiaturaController {

    private final OrganizzazioneRepository organizzazioneRepository;
    private final ApparecchiaturaRepository apparecchiaturaRepository;

    @Inject
    public ApparecchiaturaController(OrganizzazioneRepository organizzazioneRepository,
                                     ApparecchiaturaRepository apparecchiaturaRepository) {
        this.organizzazioneRepository = organizzazioneRepository;
        this.apparecchiaturaRepository = apparecchiaturaRepository;
    }

    @GET
    @Path("/organizzazioni")
    @Authenticated
    public List<Organizzazione> getAllOrganizations() {
        return organizzazioneRepository.findAll();
    }

    @GET
    @Path("/organizzazioni/{id}/tree")
    @Authenticated
    public Organizzazione tree(@PathParam("id") Long id) {
        return organizzazioneRepository.findById(id)
                .orElseThrow(() -> new WebApplicationException("Organizzazione non trovata con id: " + id, 404));
    }

    @POST
    @Path("/apparecchiatura")
    @RolesAllowed("ADMIN")
    public Map<String, String> create(Apparecchiatura apparecchiatura) {
        apparecchiaturaRepository.save(apparecchiatura);
        return Map.of("status", "created");
    }

}