package com.example.cmd.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Panier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    private List<ProductDto> produits;

    @ManyToOne
    private Client client;

    private Double total;

    // Getters and Setters
}
