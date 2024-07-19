package com.example.cmd.service;

import com.example.cmd.model.Attribut;
import com.example.cmd.model.Produit;

import java.util.List;
import java.util.Optional;

public interface AttributService {
    List<Attribut> attributListe();
    Optional<Attribut> trouverParId(Long id);
    Attribut ajouterAttribut(Attribut attribut);
    Attribut modifierAttribut(Long id, Attribut attribut);
    void supprimerAttribut(Long id);
}
