package com.example.cmd.service;

import com.example.cmd.model.Attribut;
import com.example.cmd.model.Produit;

import java.util.List;
import java.util.Optional;

public interface ProduitService {
    List<Produit> produitListe();
    Optional<Produit> trouverParId(Long id);
    Produit ajouterProduit(Produit produit);
    Produit modifierProduit(Long id, Produit produit);
    void supprimerProduit(Long id);
}
