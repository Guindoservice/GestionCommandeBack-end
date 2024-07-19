package com.example.cmd.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "PRODUIT")
@Getter
@Setter
@NoArgsConstructor
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String description;
    private Float prix;
    private Float quantite;
    private LocalDate date = LocalDate.now();

    // Supprimé la colonne id_utilisateur
    // @ManyToOne
    // @JoinColumn(name = "id_utilisateur")
    // private Utilisateur utilisateur;

    @ManyToOne
    @JoinColumn(name = "id_categorie")
    private Categorie categorie;

    public Produit(String nom, String description, Categorie categorie, Float prix, Float quantite) {
        this.nom = nom;
        this.description = description;
        this.categorie = categorie;
        this.prix = prix;
        this.quantite = quantite;
    }
}
