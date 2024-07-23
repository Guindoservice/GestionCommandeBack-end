package com.example.cmd.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Entity
public class Panier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "panier", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PanierProduit> panierProduits = new ArrayList<>();


    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    private BigDecimal total;


    public void ajouterProduit(Produit produit, int quantite) {
        if (panierProduits == null) {
            panierProduits = new ArrayList<>();
        }

        PanierProduit panierProduit = panierProduits.stream()
                .filter(pp -> pp.getProduit().getId().equals(produit.getId()))
                .findFirst()
                .orElse(new PanierProduit());

        panierProduit.setProduit(produit);
        panierProduit.setQuantite(panierProduit.getQuantite() + quantite);
        panierProduit.setPanier(this);

        if (!panierProduits.contains(panierProduit)) {
            panierProduits.add(panierProduit);
        }
        recalculerTotal();
    }


    public void modifierQuantiteProduit(Long produitId, int nouvelleQuantite) {
        PanierProduit panierProduit = panierProduits.stream()
                .filter(pp -> pp.getProduit().getId().equals(produitId))
                .findFirst()
                .orElse(null);

        if (panierProduit != null) {
            panierProduit.setQuantite(nouvelleQuantite);
            recalculerTotal();
        }
    }

    public void supprimerProduit(Long produitId) {
        panierProduits.removeIf(pp -> pp.getProduit().getId().equals(produitId));
        recalculerTotal();
    }

    public void recalculerTotal() {
        total = panierProduits.stream()
                .map(PanierProduit::getTotalProduit)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Collection<Object> getProduits() {
        return getProduits();
    }
}
