package com.example.cmd.service;

import com.example.cmd.model.Panier;
import com.example.cmd.model.ProductDto;
import com.example.cmd.repository.PanierRepository;
import com.example.cmd.repository.UtilisateurRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class PanierService {

    private final PanierRepository panierRepository;
    private final UtilisateurRepository utilisateurRepository;

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

    public Panier ajouterProduitAuPanier(Long panierId, ProductDto produit) {
        Panier panier = panierRepository.findById(panierId)
                .orElseThrow(() -> new IllegalArgumentException("Panier non trouvé avec id : " + panierId));
        panier.ajouterProduit(produit);
        return panierRepository.save(panier);
    }

    public Panier modifierQuantiteProduitDansPanier(Long panierId, Long produitId, int nouvelleQuantite) {
        Panier panier = panierRepository.findById(panierId)
                .orElseThrow(() -> new IllegalArgumentException("Panier non trouvé avec id : " + panierId));
        panier.modifierQuantiteProduit(produitId, nouvelleQuantite);
        return panierRepository.save(panier);
    }

    public Panier supprimerProduitDuPanier(Long panierId, Long produitId) {
        Panier panier = panierRepository.findById(panierId)
                .orElseThrow(() -> new IllegalArgumentException("Panier non trouvé avec id : " + panierId));
        panier.supprimerProduit(produitId);
        return panierRepository.save(panier);
    }

    public String payerProduitsDansPanier(Long panierId) {
        Panier panier = panierRepository.findById(panierId)
                .orElseThrow(() -> new IllegalArgumentException("Panier non trouvé avec id : " + panierId));
        if (panier.getClient().getSolde().compareTo(panier.getTotal()) < 0) {
            return "Solde insuffisant pour effectuer le paiement.";
        }
        panier.getClient().setSolde(panier.getClient().getSolde().subtract(panier.getTotal()));
        panierRepository.save(panier);
        utilisateurRepository.save(panier.getClient());
        return "Paiement effectué avec succès.";
    }

    public String payerProduitsSelectionnesDansPanier(Long panierId, List<ProductDto> produitsSelectionnes) {
        Panier panier = panierRepository.findById(panierId)
                .orElseThrow(() -> new IllegalArgumentException("Panier non trouvé avec id : " + panierId));
        BigDecimal totalSelection = BigDecimal.ZERO;
        for (ProductDto produitSelectionne : produitsSelectionnes) {
            for (ProductDto produit : panier.getProduits()) {
                if (produit.getId().equals(produitSelectionne.getId())) {
                    totalSelection = totalSelection.add(produit.getPrix().multiply(BigDecimal.valueOf(produitSelectionne.getQuantite())));
                }
            }
        }
        if (panier.getClient().getSolde().compareTo(totalSelection) < 0) {
            return "Solde insuffisant pour effectuer le paiement.";
        }
        panier.getClient().setSolde(panier.getClient().getSolde().subtract(totalSelection));
        utilisateurRepository.save(panier.getClient());
        for (ProductDto produitSelectionne : produitsSelectionnes) {
            panier.modifierQuantiteProduit(produitSelectionne.getId(), produitSelectionne.getQuantite());
        }
        panier.recalculerTotal();
        panierRepository.save(panier);
        return "Paiement des produits sélectionnés effectué avec succès.";
    }
}
