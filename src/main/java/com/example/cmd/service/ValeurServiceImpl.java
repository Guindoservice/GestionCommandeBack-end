package com.example.cmd.service;

import com.example.cmd.model.Valeur;
import com.example.cmd.repository.ValeurRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@AllArgsConstructor
@Service
public class ValeurServiceImpl implements ValeurService {


    private final ValeurRepository valeurRepository;

    @Override
    public List<Valeur> valeurListe() {
        return valeurRepository.findAll();
    }

    @Override
    public Optional<Valeur> trouverParId(Long id) {
        return valeurRepository.findById(id);
    }

    @Override
    public Valeur ajouterValeur(Valeur valeur) {
        return valeurRepository.save(valeur);
    }

    @Override
    public Valeur modifierValeur(Long id, Valeur valeur) {
        if (valeurRepository.existsById(id)) {
            valeur.setId(id);
            return valeurRepository.save(valeur);
        } else {
            throw new EntityNotFoundException("Valeur non trouvée avec l'id : " + id);
        }
    }

    @Override
    public void supprimerValeur(Long id) {
        if (valeurRepository.existsById(id)) {
            valeurRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Valeur non trouvée avec l'id : " + id);
        }
    }
}
