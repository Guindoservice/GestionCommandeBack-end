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
@Table(name = "commande")
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_commande;
    private LocalDate date;
    private String status;
    private Integer quantiteCommande;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_client")
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_produit")
    private ProductDto product;

    @Column(name = "totalAmount")
    private BigDecimal totalAmount;

    // Ajout de l'attribut Utilisateur
    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;

    @ManyToOne
    @JoinColumn(name = "deliverer_id")
    private Deliverer deliverer;

    // Constructeurs, getters et setters
    public Commande() {
    }

    public Commande(LocalDate date, String status, Integer quantiteCommande, Client client, ProductDto product) {
        this.date = date;
        this.status = status;
        this.quantiteCommande = quantiteCommande;
        this.client = client;
        this.product = product;
        this.totalAmount = calculateTotalAmount();
    }

    private BigDecimal calculateTotalAmount() {
        return product.getPrix().multiply(BigDecimal.valueOf(quantiteCommande));
    }

    public List<ProductDto> getProducts(List<ProductDto> products) {
        return products;
    }
}
