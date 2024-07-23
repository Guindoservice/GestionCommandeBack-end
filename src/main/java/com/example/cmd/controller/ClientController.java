package com.example.cmd.controller;

import com.example.cmd.DTO.AvisDTO;
import com.example.cmd.DTO.ChangePasswordDto;
import com.example.cmd.config.CustomUserPrincipal;
import com.example.cmd.model.Avis;
import com.example.cmd.model.Panier;
import com.example.cmd.service.AvisService;
import com.example.cmd.service.ClientService;
import com.example.cmd.service.PanierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;
    @Autowired
    private AvisService avisService;
    @Autowired
    private PanierService panierService;

    @GetMapping("/{clientId}/profil")
    public ResponseEntity<String> afficherProfil(@PathVariable Long clientId) {
        if (!clientService.estCompteActif(clientId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Accès refusé : Compte désactivé.");
        }
        return ResponseEntity.ok("Votre compte est activé");
    }

    @PostMapping("/{clientId}/change-password")
    public ResponseEntity<?> changePassword(@PathVariable Long clientId, @RequestBody ChangePasswordDto changePasswordDto) {
        try {
            clientService.changePassword(clientId, changePasswordDto);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/CreerAvis")
    public ResponseEntity<Avis> createAvis(@RequestBody AvisDTO avisDTO) {
        Long clientId = getAuthenticatedClientId();
        Avis createdAvis = avisService.createAvis(avisDTO, clientId);
        return new ResponseEntity<>(createdAvis, HttpStatus.CREATED);
    }

    @PutMapping("/modifierAvis/{id}")
    public ResponseEntity<Avis> updateAvis(@PathVariable Long id, @RequestBody Avis avisDetails) {
        Avis updatedAvis = avisService.modifierAvis(id, avisDetails);
        return new ResponseEntity<>(updatedAvis, HttpStatus.OK);
    }

    @DeleteMapping("/SupprimerAvis/{id}")
    public ResponseEntity<String> deleteAvis(@PathVariable Long id) {
        String message = avisService.supprimerAvis(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    private Long getAuthenticatedClientId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof CustomUserPrincipal) {
                CustomUserPrincipal customPrincipal = (CustomUserPrincipal) principal;
                if (customPrincipal.getClient() != null) {
                    return customPrincipal.getClient().getId();
                } else {
                    throw new IllegalArgumentException("Authenticated user is not a client");
                }
            }
            throw new IllegalArgumentException("Principal is not an instance of CustomUserPrincipal");
        }
        throw new IllegalArgumentException("User is not authenticated");
    }

    @PostMapping("/{clientId}/panier/ajouterProduit")
    public ResponseEntity<Panier> ajouterProduitAuPanier(@PathVariable Long clientId, @RequestParam Long produitId, @RequestParam int quantite) {
        Panier panier = panierService.ajouterProduitAuPanier(clientId, produitId, quantite);
        return new ResponseEntity<>(panier, HttpStatus.OK);
    }


    @PutMapping("/{clientId}/panier/{panierId}/modifierQuantite")
    public ResponseEntity<Panier> modifierQuantiteProduitDansPanier(@PathVariable Long clientId, @PathVariable Long panierId, @RequestParam Long produitId, @RequestParam int nouvelleQuantite) {
        Panier panier = panierService.modifierQuantiteProduitDansPanier(panierId, produitId, nouvelleQuantite);
        return new ResponseEntity<>(panier, HttpStatus.OK);
    }

    @DeleteMapping("/{clientId}/panier/{panierId}/supprimerProduit")
    public ResponseEntity<Panier> supprimerProduitDuPanier(@PathVariable Long clientId, @PathVariable Long panierId, @RequestParam Long produitId) {
        Panier panier = panierService.supprimerProduitDuPanier(panierId, produitId);
        return new ResponseEntity<>(panier, HttpStatus.OK);
    }

    @DeleteMapping("/{clientId}/panier/{panierId}")
    public ResponseEntity<String> supprimerPanier(@PathVariable Long clientId, @PathVariable Long panierId) {
        String message = panierService.supprimerPanier(panierId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PostMapping("/payer/panier/{panierId}")
    public ResponseEntity<String> payerProduitsDansPanier(@PathVariable Long panierId, @RequestParam BigDecimal montantClient) {
        String resultat = panierService.payerProduitsDansPanier(panierId, montantClient);
        return new ResponseEntity<>(resultat, HttpStatus.OK);
    }

}
