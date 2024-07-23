package com.example.cmd.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "PRODUIT")
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private BigDecimal prix;
    private int quantite;
    private String libelle;

    private LocalDate date = LocalDate.now();

    @ManyToOne
    @JoinColumn(name = "id_utilisateur")
    private Utilisateur utilisateur;

    @Getter
    @Setter
    @Transient // Indique que ce champ ne doit pas être persisté
    private Long id_category;

    @OneToMany(mappedBy = "produit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Variante> variantes;

    public void addVariante(Variante variante) {
        variantes.add(variante);
        variante.setProduit(this);
    }

    public void removeVariante(Variante variante) {
        variantes.remove(variante);
        variante.setProduit(null);
    }

    public Long getId() {
        return id;
    }
}
