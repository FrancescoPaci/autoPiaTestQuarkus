package com.example.radiology.entity;

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
@Table(name = "organizzazione")
public class Organizzazione {

    @Id
    @EqualsAndHashCode.Include // 👈 Indica a Lombok di usare solo l'ID
    private Long id;

    @Column(nullable = false)
    private String nome;

    // 🌟 Uso @JsonManagedReference: dice a Jackson di serializzare le apparecchiature,
    // ma si coordinerà con il @JsonBackReference che metterai dentro l'entità Apparecchiatura
    @OneToMany(mappedBy = "organizzazione", fetch = FetchType.LAZY)
    @JsonManagedReference(value = "organizzazione-apparecchiature")
    @Builder.Default
    private Set<Apparecchiatura> apparecchiatureDirette = new LinkedHashSet<>();

    @OneToMany(mappedBy = "organizzazione", fetch = FetchType.LAZY)
    @JsonManagedReference(value = "organizzazione-contenitori")
    @Builder.Default
    private Set<Contenitore> contenitori = new LinkedHashSet<>();

}