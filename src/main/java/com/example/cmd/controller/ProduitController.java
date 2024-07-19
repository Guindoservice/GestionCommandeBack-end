package com.example.cmd.controller;

import com.example.cmd.Dto.ProduitDto;
import com.example.cmd.Dto.VarianteDto;
import com.example.cmd.model.*;
import com.example.cmd.repository.AttributRepository;
import com.example.cmd.repository.ProduitRepository;
import com.example.cmd.repository.ValeurRepository;
import com.example.cmd.service.ProduitService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/produits")
public class ProduitController {

    private ProduitService produitService;
    private AttributRepository attributRepository;
    private ValeurRepository valeurRepository;

    @PostMapping("/ajouter")
    public ResponseEntity<Produit> ajouterProduit(@RequestBody ProduitDto produitDto) {
        Produit produit = new Produit();
        produit.setNom(produitDto.getNom());
        produit.setDescription(produitDto.getDescription());

        if (produitDto.isAVariante()) {
            List<Variante> variantes = new ArrayList<>();
            for (VarianteDto varianteDto : produitDto.getVariantes()) {
                Variante variante = new Variante();
                variante.setProduit(produit);
                variante.setPrix(varianteDto.getPrix());
                variante.setStock(varianteDto.getStock());

                // Assurez-vous que les IDs d'attribut et de valeur existent
                Attribut attribut = attributRepository.findById(varianteDto.getAttributId())
                        .orElseThrow(() -> new RuntimeException("Attribut non trouvé avec l'ID : " + varianteDto.getAttributId()));
                Valeur valeur = valeurRepository.findById(varianteDto.getValeurId())
                        .orElseThrow(() -> new RuntimeException("Valeur non trouvée avec l'ID : " + varianteDto.getValeurId()));

                // Créer et associer l'AttributValeur avec la variante
                AttributValeur attributValeur = new AttributValeur();
                attributValeur.setAttribut(attribut);
                attributValeur.setValeur(valeur);
                attributValeur.setVariante(variante);

                variante.getAttributValeurs().add(attributValeur);
                variantes.add(variante);
            }
            produit.setVariantes(variantes);
        } else {
            produit.setPrix(produitDto.getPrix());
            produit.setStock(produitDto.getStock());
        }

        Produit nouveauProduit = produitService.ajouterProduit(produit);
        return ResponseEntity.status(HttpStatus.CREATED).body(nouveauProduit);
    }
}