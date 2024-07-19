package com.example.cmd.service;

import com.example.cmd.model.Produit;
import com.example.cmd.model.Variante;
import com.example.cmd.repository.ProduitRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@AllArgsConstructor
@Service
public class ProduitServiceImpl implements ProduitService {


    private ProduitRepository produitRepository;

    @Override
    public List<Produit> produitListe() {
        return produitRepository.findAll();
    }

    @Override
    public Optional<Produit> trouverParId(Long id) {
        return produitRepository.findById(id);
    }

    @Override
    public Produit ajouterProduit(Produit produit) {

        if (produit.getVariantes() != null && !produit.getVariantes().isEmpty()) {
            for (Variante variante : produit.getVariantes()) {
                variante.setProduit(produit);
            }
        }
        Produit nouveauProduit = produitRepository.save(produit);

        return nouveauProduit;
    }

    @Override
    public Produit modifierProduit(Long id, Produit produit) {
        if (produitRepository.existsById(id)) {
            produit.setId(id);
            return produitRepository.save(produit);
        } else {
            throw new EntityNotFoundException("Produit non trouvé avec l'id : " + id);
        }
    }

    @Override
    public void supprimerProduit(Long id) {
        if (produitRepository.existsById(id)) {
            produitRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Produit non trouvé avec l'id : " + id);
        }
    }
}
