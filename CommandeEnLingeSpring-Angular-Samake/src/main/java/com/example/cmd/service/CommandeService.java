package com.example.cmd.service;

import com.example.cmd.model.Commande;
import com.example.cmd.model.ProductDto;
import com.example.cmd.model.Utilisateur;
import com.example.cmd.repository.CommandeRepository;
import com.example.cmd.repository.ProductRepository;
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
    private ProductRepository productRepository;

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

        if (!"CLIENT".equals(client.getRoleType())) {
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

    @Transactional
    public String payerCommande(Long clientId, List<ProductDto> products) {
        Optional<Utilisateur> clientOptional = utilisateurRepository.findById(clientId);
        if (!clientOptional.isPresent()) {
            return "Client non trouvé avec id : " + clientId;
        }

        Utilisateur client = clientOptional.get();

        if (!"CLIENT".equals(client.getRoleType())) {
            return "L'utilisateur sélectionné n'est pas un client.";
        }

        BigDecimal totalMontant = calculerMontantTotalCommandesClient(clientId, products);

        if (client.getSolde().compareTo(totalMontant) < 0) {
            return "Solde insuffisant.";
        }

        // Débiter le compte du client
        client.setSolde(client.getSolde().subtract(totalMontant));
        utilisateurRepository.save(client);

        // Mettre à jour la quantité des produits dans la base de données
        for (ProductDto produit : products) {
            Optional<ProductDto> productOptional = productRepository.findById(produit.getId());
            if (productOptional.isPresent()) {
                ProductDto product = productOptional.get();
                if (product.getQuantite() < produit.getQuantite()) {
                    return "Quantité insuffisante pour le produit : " + product.getName();
                }
                product.setQuantite(product.getQuantite() - produit.getQuantite());
                productRepository.save(product);
            } else {
                return "Produit non trouvé avec id : " + produit.getId();
            }
        }

        return "Paiement réussi.";
    }

    // Nouvelle méthode pour assigner une commande payée à un livreur
    public String assignCommandToDeliverer(Long commandeId, Long livreurId) {
        Optional<Commande> commandeOptional = commandeRepository.findById(commandeId);
        if (!commandeOptional.isPresent()) {
            return "Commande non trouvée.";
        }

        Commande commande = commandeOptional.get();
        if (!commande.getStatus().equals("PAYE")) { // Supposons que le statut "PAID" indique que la commande est payée
            return "La commande doit être payée avant d'être assignée à un livreur.";
        }

        Optional<Utilisateur> livreurOptional = utilisateurRepository.findById(livreurId);
        if (!livreurOptional.isPresent()) {
            return "Livreur non trouvé.";
        }

        Utilisateur livreur = livreurOptional.get();
        if (!"LIVREUR".equals(livreur.getRoleType())) {
            return "Le livreur sélectionné n'est pas un livreur.";
        }

        commande.setUtilisateur(livreur);
        commandeRepository.save(commande);
        return "Commande assignée avec succès au livreur.";
    }


}
