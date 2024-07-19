package com.example.cmd.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
public class Panier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductDto> produits;

    @ManyToOne
    private Client client;

    private BigDecimal total;

    public void ajouterProduit(ProductDto produit) {
        this.produits.add(produit);
        recalculerTotal();
    }

    public void modifierQuantiteProduit(Long produitId, int nouvelleQuantite) {
        for (ProductDto produit : produits) {
            if (produit.getId().equals(produitId)) {
                produit.setQuantite(nouvelleQuantite);
                recalculerTotal();
                return;
            }
        }
    }

    public void supprimerProduit(Long produitId) {
        produits.removeIf(produit -> produit.getId().equals(produitId));
        recalculerTotal();
    }

    public void recalculerTotal() {
        total = BigDecimal.ZERO;
        for (ProductDto produit : produits) {
            total = total.add(produit.getPrix().multiply(BigDecimal.valueOf(produit.getQuantite())));
        }
    }
}
