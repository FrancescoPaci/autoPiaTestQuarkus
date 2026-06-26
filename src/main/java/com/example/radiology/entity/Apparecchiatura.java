package com.example.radiology.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
// 🌟 Dice a Lombok di generare equals/hashCode usando SOLO i campi contrassegnati
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "apparecchiatura")
public class Apparecchiatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include // 👈 Indica a Lombok di usare solo l'ID
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String tipologia;

    @Column(name = "numero_serie", nullable = false, unique = true)
    private String numeroSerie;

    @Column(name = "data_installazione", nullable = false)
    private LocalDate dataInstallazione;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_organizzazione")
    @JsonBackReference(value = "organizzazione-apparecchiature")
    private Organizzazione organizzazione;

    // 🌟 Ottimizzato: Fetch LAZY e BackReference per Jackson coordinato con Contenitore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_contenitore")
    @JsonBackReference(value = "contenitore-apparecchiature")
    private Contenitore contenitore;

    // Controllo di integrità (Ottimo, rimane identico!)
    @PrePersist
    @PreUpdate
    private void validazioneEsclusivita() {
        if (this.organizzazione != null && this.contenitore != null) {
            throw new IllegalStateException("Un'apparecchiatura non può essere legata contemporaneamente a un'organizzazione e a un contenitore.");
        }
        if (this.organizzazione == null && this.contenitore == null) {
            throw new IllegalStateException("L'apparecchiatura deve essere associata a un'organizzazione o a un contenitore.");
        }
    }

}