package com.example.cmd.service;

import com.example.cmd.model.Historique;
import com.example.cmd.model.Utilisateur;
import com.example.cmd.repository.HistoriqueRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class HistoriqueServiceImpl implements HistoriqueService{

    private HistoriqueRepository historiqueRepository;

    @Override
    public Historique createHistorique(Historique historique) {
        return this.historiqueRepository.save(historique);
    }

    @Override
    public List<Historique> voirToutesHistoriques() {
        return this.historiqueRepository.findAll();
    }

    @Override
    public List<Historique> voirToutesHistoriquesPar(Utilisateur utilisateur) {
        return this.historiqueRepository.findAllBy_faitPar(utilisateur);
    }
}
