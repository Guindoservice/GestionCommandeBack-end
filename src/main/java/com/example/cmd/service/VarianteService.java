package com.example.cmd.service;

import com.example.cmd.model.Variante;
import java.util.List;
import java.util.Optional;
public interface VarianteService {
        List<Variante> varianteListe();
        Optional<Variante> trouverParId(Long id);
        Variante ajouterVariante(Variante variante);
        Variante modifierVariante(Long id, Variante variante);
        void supprimerVariante(Long id);
    }

