package com.example.cmd.service;

import com.example.cmd.model.Avis;
import com.example.cmd.repository.AvisRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AvisService {

    private final AvisRepository avisRepository;

    public Avis ajouterAvis(Avis avis) {
        return avisRepository.save(avis);
    }

    public List<Avis> lireAvisParProduit(Long produitId) {
        return avisRepository.findByProduitId(produitId);
    }

    public List<Avis> lireAvisParClient(Long clientId) {
        return avisRepository.findByClientId(clientId);
    }

    public Avis modifierAvis(Long id, Avis avisDetails) {
        Avis avis = avisRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Avis non trouvé avec id : " + id));
        avis.setCommentaire(avisDetails.getCommentaire());
        avis.setNote(avisDetails.getNote());
        return avisRepository.save(avis);
    }

    public String supprimerAvis(Long id) {
        avisRepository.deleteById(id);
        return "Avis supprimé avec succès!";
    }
}
