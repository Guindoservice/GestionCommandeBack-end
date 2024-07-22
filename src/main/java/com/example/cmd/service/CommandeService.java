package com.example.cmd.service;

import com.example.cmd.model.Commande;
import com.example.cmd.model.ProductDto;
import com.example.cmd.model.RoleType;
import com.example.cmd.model.Utilisateur;
import com.example.cmd.repository.CommandeRepository;
import com.example.cmd.repository.UtilisateurRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CommandeService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private CommandeRepository commandeRepository;

    public List<Commande> getAllCommandes() {
        return commandeRepository.findAll();
    }

    public Commande getCommandeById(Long id) {
        return commandeRepository.findById(id).orElse(null);
    }

    public Commande saveCommande(Commande commande) {
        return commandeRepository.save(commande);
    }

    public void deleteCommande(Long id) {
        commandeRepository.deleteById(id);
    }

    public List<Commande> getCommandesByDelivererId(Long delivererId) {
        return commandeRepository.findByDeliverer_Id(delivererId);
    }

    @Transactional
    public BigDecimal calculerMontantTotalCommandesClient(Long clientId, List<ProductDto> products) {
        Optional<Utilisateur> clientOptional = utilisateurRepository.findById(clientId);
        if (!clientOptional.isPresent()) {
            throw new IllegalArgumentException("Client non trouvé avec id : " + clientId);
        }

        Utilisateur client = clientOptional.get();

        if (!client.getRole().equals(RoleType.CLIENT)) {
            throw new IllegalArgumentException("L'utilisateur sélectionné n'est pas un client.");
        }

        List<Commande> commandesDuClient = commandeRepository.findByUtilisateurId(clientId);
        BigDecimal total = BigDecimal.ZERO;

        for (Commande commande : commandesDuClient) {
            for (ProductDto produit : commande.getProducts(products)) { // Supposons que chaque commande contient une liste de produits
                BigDecimal coutUnitaire = produit.getPrix().multiply(BigDecimal.valueOf(produit.getQuantite()));
                total = total.add(coutUnitaire);
            }
        }

        return total;
    }


}
