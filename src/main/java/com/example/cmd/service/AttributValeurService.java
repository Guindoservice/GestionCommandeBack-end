package com.example.cmd.service;

import com.example.cmd.model.AttributValeur;
import com.example.cmd.model.Valeur;

import java.util.List;
import java.util.Optional;

public interface AttributValeurService {
    List<AttributValeur> attributValeurListe();
    Optional<AttributValeur> trouverParId(Long id);
    AttributValeur ajouterAttributValeur(AttributValeur attributValeur);
    AttributValeur modifierAttributValeur(Long id, AttributValeur attributValeur);
    void supprimerAttributValeur(Long id);
}