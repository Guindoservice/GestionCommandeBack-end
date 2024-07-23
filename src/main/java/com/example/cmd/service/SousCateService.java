package com.example.cmd.service;

import com.example.cmd.model.SousCategory;

import java.util.List;

public interface SousCateService {

    SousCategory ajouterSousCate(SousCategory sousCategory);
    List<SousCategory> listerSousCate();
    SousCategory modifierSousCate(SousCategory sousCategory, Long id);
    String supprimerSousCate(Long id);
}
