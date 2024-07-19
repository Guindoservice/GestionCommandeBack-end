package com.example.cmd.controller;

import com.example.cmd.model.Panier;
import com.example.cmd.model.ProductDto;
import com.example.cmd.service.PanierService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/paniers")
@AllArgsConstructor
public class PanierController {

    private final PanierService panierService;

    @PostMapping
    public Panier ajouterPanier(@RequestBody Panier panier) {
        return panierService.ajouterPanier(panier);
    }

    @GetMapping
    public List<Panier> lirePaniers() {
        return panierService.lirePaniers();
    }

    @PutMapping("/{id}")
    public Panier modifierPanier(@PathVariable Long id, @RequestBody Panier panierDetails) {
        return panierService.modifierPanier(id, panierDetails);
    }

    @DeleteMapping("/{id}")
    public String supprimerPanier(@PathVariable Long id) {
        return panierService.supprimerPanier(id);
    }

    @PostMapping("/{panierId}/ajouter-produit")
    public Panier ajouterProduitAuPanier(@PathVariable Long panierId, @RequestBody ProductDto produit) {
        return panierService.ajouterProduitAuPanier(panierId, produit);
    }

    @PutMapping("/{panierId}/modifier-produit/{produitId}")
    public Panier modifierQuantiteProduitDansPanier(@PathVariable Long panierId, @PathVariable Long produitId, @RequestParam int nouvelleQuantite) {
        return panierService.modifierQuantiteProduitDansPanier(panierId, produitId, nouvelleQuantite);
    }

    @DeleteMapping("/{panierId}/supprimer-produit/{produitId}")
    public Panier supprimerProduitDuPanier(@PathVariable Long panierId, @PathVariable Long produitId) {
        return panierService.supprimerProduitDuPanier(panierId, produitId);
    }

    @PostMapping("/{panierId}/payer")
    public String payerProduitsDansPanier(@PathVariable Long panierId) {
        return panierService.payerProduitsDansPanier(panierId);
    }

    @PostMapping("/{panierId}/payer-selection")
    public String payerProduitsSelectionnesDansPanier(@PathVariable Long panierId, @RequestBody List<ProductDto> produitsSelectionnes) {
        return panierService.payerProduitsSelectionnesDansPanier(panierId, produitsSelectionnes);
    }
}
