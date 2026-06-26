package com.example.radiology.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
// 🌟 Dice a Lombok di generare equals/hashCode usando SOLO i campi contrassegnati
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(
        name = "contenitore",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_organizzazione_ordine",
                        columnNames = {"id_organizzazione", "ordine"} // Mantiene l'ottimo vincolo di unicità
                )
        }
)
public class Contenitore {

    @Id
    @EqualsAndHashCode.Include // 👈 Indica a Lombok di usare solo l'ID
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private Integer ordine;

    // 🌟 Ottimizzato: Fetch LAZY e BackReference verso il padre (Organizzazione)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_organizzazione", nullable = false)
    @JsonBackReference(value = "organizzazione-contenitori")
    private Organizzazione organizzazione;

    // 🌟 Ottimizzato: ManagedReference verso i figli (Apparecchiatura)
    @OneToMany(mappedBy = "contenitore", fetch = FetchType.LAZY)
    @JsonManagedReference(value = "contenitore-apparecchiature")
    @Builder.Default
    private Set<Apparecchiatura> apparecchiature = new LinkedHashSet<>();

}