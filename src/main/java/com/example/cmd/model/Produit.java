package com.example.cmd.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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
    private Float prix;
    private int quantite;
    private String libelle;
    LocalDate date = LocalDate.now();

    @ManyToOne
    @JoinColumn(name = "id_utilisateur")
    private Utilisateur utilisateur;

    @Getter
    @Setter
    @Transient // Indique que ce champ ne doit pas être persisté
    private Long id_category;


    @OneToMany(mappedBy = "produit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Variante> variantes;

//    public Produit(String description,  Long id_category, Float prix, int quantite, Utilisateur utilisateur) {
//        this.description = description;
//        this.id_category = id_category;
//        this.prix = prix;
//        this.quantite = quantite;
//        this.utilisateur = utilisateur;
//    }
//
//    public Produit() {
//
//    }

    public void addVariante(Variante variante) {
        variantes.add(variante);
        variante.setProduit(this);
    }

    public void removeVariante(Variante variante) {
        variantes.remove(variante);
        variante.setProduit(null);
    }


}
