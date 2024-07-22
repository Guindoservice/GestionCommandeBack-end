package com.example.cmd.service;

import com.example.cmd.model.Panier;
import com.example.cmd.repository.PanierRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PanierService {

    private final PanierRepository panierRepository;

    public Panier ajouterPanier(Panier panier) {
        return panierRepository.save(panier);
    }

    public List<Panier> lirePaniers() {
        return panierRepository.findAll();
    }

    public Panier modifierPanier(Long id, Panier panierDetails) {
        Panier panier = panierRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Panier non trouvé avec id : " + id));
        panier.setProduits(panierDetails.getProduits());
        panier.setClient(panierDetails.getClient());
        panier.setTotal(panierDetails.getTotal());
        return panierRepository.save(panier);
    }

    public String supprimerPanier(Long id) {
        panierRepository.deleteById(id);
        return "Panier supprimé avec succès!";
    }
}
