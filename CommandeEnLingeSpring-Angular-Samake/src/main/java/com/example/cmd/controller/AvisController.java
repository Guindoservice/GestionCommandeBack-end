package com.example.cmd.controller;

import com.example.cmd.model.Avis;
import com.example.cmd.service.AvisService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/avis")
@AllArgsConstructor
public class AvisController {

    private final AvisService avisService;

    @PostMapping
    public Avis ajouterAvis(@RequestBody Avis avis) {
        return avisService.ajouterAvis(avis);
    }

    @GetMapping("/produit/{produitId}")
    public List<Avis> lireAvisParProduit(@PathVariable Long produitId) {
        return avisService.lireAvisParProduit(produitId);
    }

    @GetMapping("/client/{clientId}")
    public List<Avis> lireAvisParClient(@PathVariable Long clientId) {
        return avisService.lireAvisParClient(clientId);
    }

    @PutMapping("/{id}")
    public Avis modifierAvis(@PathVariable Long id, @RequestBody Avis avisDetails) {
        return avisService.modifierAvis(id, avisDetails);
    }

    @DeleteMapping("/{id}")
    public String supprimerAvis(@PathVariable Long id) {
        return avisService.supprimerAvis(id);
    }
}
