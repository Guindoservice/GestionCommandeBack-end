package com.example.cmd.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "CATEGORIE")
@Getter
@Setter
@NoArgsConstructor
public class Categorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    public Categorie(String nom) {
        this.nom = nom;
    }
}
