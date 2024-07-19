package com.example.cmd.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Avis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Client client;

    @ManyToOne
    private ProductDto produit;

    private String commentaire;
    private int note; // Par exemple, de 1 à 5 étoiles

    // Getters et Setters
}
