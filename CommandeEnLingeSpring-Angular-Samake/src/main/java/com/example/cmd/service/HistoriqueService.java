package com.example.cmd.service;

import com.example.cmd.model.Historique;
import com.example.cmd.model.Utilisateur;

import java.util.List;

public interface HistoriqueService {
    Historique createHistorique(Historique historique);
    List<Historique> voirToutesHistoriques();
    List<Historique> voirToutesHistoriquesPar(Utilisateur utilisateur);
}
