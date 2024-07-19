package com.example.cmd.controller;

import com.example.cmd.model.AttributValeur;
import com.example.cmd.service.AttributValeurService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@AllArgsConstructor
@RestController
@RequestMapping("/attributs-valeur")
public class AttributValeurController {


    private AttributValeurService attributValeurService;

    @GetMapping
    public ResponseEntity<List<AttributValeur>> listeAttributsValeurs() {
        List<AttributValeur> attributsValeurs = attributValeurService.attributValeurListe();
        return ResponseEntity.ok().body(attributsValeurs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttributValeur> trouverAttributValeurParId(@PathVariable Long id) {
        return attributValeurService.trouverParId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AttributValeur> ajouterAttributValeur(@RequestBody AttributValeur attributValeur) {
        AttributValeur nouvelleAttributValeur = attributValeurService.ajouterAttributValeur(attributValeur);
        return ResponseEntity.status(HttpStatus.CREATED).body(nouvelleAttributValeur);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AttributValeur> modifierAttributValeur(@PathVariable Long id, @RequestBody AttributValeur attributValeur) {
        AttributValeur attributValeurModifiee = attributValeurService.modifierAttributValeur(id, attributValeur);
        return ResponseEntity.ok().body(attributValeurModifiee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerAttributValeur(@PathVariable Long id) {
        attributValeurService.supprimerAttributValeur(id);
        return ResponseEntity.noContent().build();
    }
}
