package com.example.radiology.security;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.io.IOException;

@Provider
@VerificaAzienda // 🌟 Attiva il filtro SOLO sui metodi annotati
@Priority(Priorities.AUTHORIZATION) // 🌟 Eseguito DOPO la validazione del JWT, ma prima del metodo
public class AziendaFilter implements ContainerRequestFilter {

    @Inject
    JsonWebToken jwt; // Il token qui è già validato e sicuro al 100%

    @Context
    ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        // Se il token non esiste (es. chiamata anonima), blocca
        if (jwt == null || jwt.getName() == null) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            return;
        }

        // Recuperiamo il valore dell'azienda dall'annotazione sul metodo
        VerificaAzienda annotation = resourceInfo.getResourceMethod().getAnnotation(VerificaAzienda.class);
        if (annotation == null) {
            annotation = resourceInfo.getResourceClass().getAnnotation(VerificaAzienda.class);
        }

        String aziendaRichiesta = annotation.value();
        String aziendaUtente = jwt.getClaim("azienda");

        // Se l'azienda nel JWT non coincide, blocca con 403
        if (aziendaUtente == null || !aziendaUtente.equals(aziendaRichiesta)) {
            requestContext.abortWith(Response.status(Response.Status.FORBIDDEN)
                    .entity("Accesso negato: Azienda non corrispondente.")
                    .build());
        }
    }
}