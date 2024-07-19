package com.example.cmd.controller;

import com.example.cmd.model.Valeur;
import com.example.cmd.service.ValeurService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@AllArgsConstructor
@RestController
@RequestMapping("/valeur")
public class ValeurController {


    private ValeurService valeurService;

    @GetMapping
    public ResponseEntity<List<Valeur>> listeValeurs() {
        List<Valeur> valeurs = valeurService.valeurListe();
        return ResponseEntity.ok().body(valeurs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Valeur> trouverValeurParId(@PathVariable Long id) {
        return valeurService.trouverParId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Valeur> ajouterValeur(@RequestBody Valeur valeur) {
        Valeur nouvelleValeur = valeurService.ajouterValeur(valeur);
        return ResponseEntity.status(HttpStatus.CREATED).body(nouvelleValeur);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Valeur> modifierValeur(@PathVariable Long id, @RequestBody Valeur valeur) {
        Valeur valeurModifiee = valeurService.modifierValeur(id, valeur);
        return ResponseEntity.ok().body(valeurModifiee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerValeur(@PathVariable Long id) {
        valeurService.supprimerValeur(id);
        return ResponseEntity.noContent().build();
    }
}
