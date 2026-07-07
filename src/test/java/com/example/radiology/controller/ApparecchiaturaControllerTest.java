package com.example.radiology.controller;

import com.example.radiology.entity.Apparecchiatura;
import com.example.radiology.entity.Organizzazione;
import com.example.radiology.repository.ApparecchiaturaRepository;
import com.example.radiology.repository.OrganizzazioneRepository;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.quarkus.test.security.jwt.Claim;
import io.quarkus.test.security.jwt.JwtSecurity;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@QuarkusTest
class ApparecchiaturaControllerTest {

    @InjectMock
    ApparecchiaturaRepository apparecchiaturaRepository;
    @InjectMock
    OrganizzazioneRepository organizzazioneRepository;

    @Test
    @TestSecurity(user = "user", roles = "USER")
    @JwtSecurity(claims = {
            @Claim(key = "azienda", value = "Azienda A")
    })
    void testGetAllOrganizations_Success() {
        Organizzazione org1 = Organizzazione.builder().id(1L).nome("Ospedale San Raffaele").build();
        Organizzazione org2 = Organizzazione.builder().id(2L).nome("Clinica Diagnostica Avanzata").build();

        Mockito.when(organizzazioneRepository.findAll()).thenReturn(List.of(org1, org2));

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/organizzazioni")
                .then()
                .statusCode(200)
                .body("$", hasSize(2))
                .body("[0].id", equalTo(1))
                .body("[0].nome", equalTo("Ospedale San Raffaele"));
    }

    @Test
    @TestSecurity(user = "user", roles = "USER")
    @JwtSecurity(claims = {
            @Claim(key = "azienda", value = "Azienda A")
    })
    void testGetOrganizationTree_Success() {
        Organizzazione org = Organizzazione.builder().id(10L).nome("Clinica Centrale").build();

        Mockito.when(organizzazioneRepository.findById(10L)).thenReturn(Optional.of(org));

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/organizzazioni/{id}/tree", 10L)
                .then()
                .statusCode(200)
                .body("id", equalTo(10))
                .body("nome", equalTo("Clinica Centrale"));
    }

    @Test
    @TestSecurity(user = "admin", roles = "ADMIN")
    @JwtSecurity(claims = {
            @Claim(key = "azienda", value = "Azienda A")
    })
    void testCreateApparecchiatura_Success() {
        Apparecchiatura app = Apparecchiatura.builder()
                .nome("TAC Generale")
                .tipologia("TAC")
                .numeroSerie("SN-123456")
                .build();

        Mockito.when(apparecchiaturaRepository.save(Mockito.any(Apparecchiatura.class))).thenReturn(app);

        given()
                .contentType(ContentType.JSON)
                .body(app)
                .when()
                .post("/api/apparecchiatura")
                .then()
                .statusCode(200)
                .body("status", equalTo("created"));
    }

    @Test
    @TestSecurity(user = "user", roles = "USER")
    @JwtSecurity(claims = {
            @Claim(key = "azienda", value = "Azienda A")
    })
    void testCreateApparecchiatura_ShouldFail_WhenUserIsNotAdmin() {
        Apparecchiatura app = Apparecchiatura.builder()
                .nome("TAC Generale")
                .tipologia("TAC")
                .numeroSerie("SN-123456")
                .build();

        given()
                .contentType(ContentType.JSON)
                .body(app)
                .when()
                .post("/api/apparecchiatura")
                .then()
                .statusCode(403); // Aspettato 403 Forbidden perché non è ADMIN
    }

}
