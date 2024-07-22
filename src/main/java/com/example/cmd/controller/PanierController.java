package com.example.cmd.controller;

import com.example.cmd.model.Panier;
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
}
