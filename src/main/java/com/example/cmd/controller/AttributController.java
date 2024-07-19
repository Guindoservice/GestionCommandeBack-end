package com.example.cmd.controller;

import com.example.cmd.model.Attribut;
import com.example.cmd.service.AttributService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@AllArgsConstructor
@RestController
@RequestMapping("/attribut")
public class AttributController {


    private AttributService attributService;

    @GetMapping
    public ResponseEntity<List<Attribut>> listeAttributs() {
        List<Attribut> attributs = attributService.attributListe();
        return ResponseEntity.ok().body(attributs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Attribut> trouverAttributParId(@PathVariable Long id) {
        return attributService.trouverParId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Attribut> ajouterAttribut(@RequestBody Attribut attribut) {
        Attribut nouvelAttribut = attributService.ajouterAttribut(attribut);
        return ResponseEntity.status(HttpStatus.CREATED).body(nouvelAttribut);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Attribut> modifierAttribut(@PathVariable Long id, @RequestBody Attribut attribut) {
        Attribut attributModifie = attributService.modifierAttribut(id, attribut);
        return ResponseEntity.ok().body(attributModifie);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerAttribut(@PathVariable Long id) {
        attributService.supprimerAttribut(id);
        return ResponseEntity.noContent().build();
    }
}
