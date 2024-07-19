package com.example.cmd.service;

import com.example.cmd.model.Valeur;

import java.util.List;
import java.util.Optional;

public interface ValeurService {
    List<Valeur> valeurListe();
    Optional<Valeur> trouverParId(Long id);
    Valeur ajouterValeur(Valeur valeur);
    Valeur modifierValeur(Long id, Valeur valeur);
    void supprimerValeur(Long id);
}
