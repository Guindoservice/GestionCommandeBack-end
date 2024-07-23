package com.example.cmd.service;

import com.example.cmd.model.Commande;
import com.example.cmd.model.Panier;
import com.example.cmd.model.Produit;
import com.example.cmd.repository.ClientRepository;
import com.example.cmd.repository.CommandeRepository;
import com.example.cmd.repository.PanierRepository;
import com.example.cmd.repository.ProduitRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class PanierService {

    private final PanierRepository panierRepository;
    private final ClientRepository clientRepository;
    private final ProduitRepository produitRepository;
    private final CommandeRepository commandeRepository;

    public String supprimerPanier(Long id) {
        panierRepository.deleteById(id);
        return "Panier supprimé avec succès!";
    }

    public Panier ajouterProduitAuPanier(Long clientId, Long produitId, int quantite) {
        Panier panier = panierRepository.findByClient_Id(clientId);
        if (panier == null) {
            panier = new Panier();
            panier.setClient(clientRepository.findById(clientId).orElseThrow(() -> new IllegalArgumentException("Client non trouvé")));
            panier = panierRepository.save(panier);
        }

        Produit produit = produitRepository.findById(produitId).orElseThrow(() -> new IllegalArgumentException("Produit non trouvé"));
        panier.ajouterProduit(produit, quantite);
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

    @Transactional
    public String payerProduitsDansPanier(Long panierId, BigDecimal montantClient) {
        Panier panier = panierRepository.findById(panierId)
                .orElseThrow(() -> new IllegalArgumentException("Panier non trouvé avec id : " + panierId));

        if (montantClient.compareTo(panier.getTotal()) < 0) {
            return "Montant insuffisant pour effectuer le paiement.";
        }

        BigDecimal montantRestant = montantClient.subtract(panier.getTotal());

        return "Paiement effectué avec succès. Montant restant à retourner : " + montantRestant;
    }

}
