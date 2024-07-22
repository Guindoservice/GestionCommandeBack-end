package com.example.cmd.model;


import jakarta.persistence.*;
import lombok.Data;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "livreur")
public class Deliverer extends Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String prenom;
    private String telephone;

    @OneToMany(mappedBy = "deliverer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Commande> commandes = new HashSet<>();

    public Deliverer() {
    }

    public Deliverer(String nom, String prenom, String telephone) {
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
    }

}

