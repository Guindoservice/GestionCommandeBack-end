package com.example.cmd.controller;

import com.example.cmd.model.Variante;
import com.example.cmd.service.VarianteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@AllArgsConstructor
@RestController
@RequestMapping("/variante")
public class VarianteController {


    private VarianteService varianteService;

    @GetMapping
    public ResponseEntity<List<Variante>> listeVariantes() {
        List<Variante> variantes = varianteService.varianteListe();
        return ResponseEntity.ok().body(variantes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Variante> trouverVarianteParId(@PathVariable Long id) {
        return varianteService.trouverParId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Variante> ajouterVariante(@RequestBody Variante variante) {
        Variante nouvelleVariante = varianteService.ajouterVariante(variante);
        return ResponseEntity.status(HttpStatus.CREATED).body(nouvelleVariante);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Variante> modifierVariante(@PathVariable Long id, @RequestBody Variante variante) {
        Variante varianteModifiee = varianteService.modifierVariante(id, variante);
        return ResponseEntity.ok().body(varianteModifiee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerVariante(@PathVariable Long id) {
        varianteService.supprimerVariante(id);
        return ResponseEntity.noContent().build();
    }
}
