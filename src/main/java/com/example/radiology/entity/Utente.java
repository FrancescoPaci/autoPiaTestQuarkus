package com.example.radiology.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
// Dice a Lombok di generare equals/hashCode usando SOLO i campi contrassegnati
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "utenti")
public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    private String azienda;

    private String ruoli;

    @Column(nullable = false)
    private Boolean attivo;
}