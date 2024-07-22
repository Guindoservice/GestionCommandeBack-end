package com.example.cmd.service;

import com.example.cmd.model.*;
import com.example.cmd.repository.CategoryRepository;
import com.example.cmd.repository.ProduitRepository;
import com.example.cmd.repository.UtilisateurRepository;
import com.example.cmd.repository.VarianteRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProduitService implements ProduitServiceInterface {
    private final ProduitRepository produitRepository;
    private final VarianteRepository varianteRepository;
    private final CategoryRepository categoryRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final EntreeSortiService entreeSortiServiceImp;
    private final StockService stockService;
    private final HistoriqueService historiqueService;

    public ProduitService(ProduitRepository produitRepository, VarianteRepository varianteRepository, CategoryRepository categoryRepository, UtilisateurRepository utilisateurRepository, EntreeSortiService entreeSortiServiceImp, StockService stockService, HistoriqueService historiqueService) {
        this.produitRepository = produitRepository;
        this.varianteRepository = varianteRepository;
        this.categoryRepository = categoryRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.entreeSortiServiceImp = entreeSortiServiceImp;
        this.stockService = stockService;
        this.historiqueService = historiqueService;

    }

    @Override
    @Transactional
    public Object ajouterProduit(Produit produit) {
        Long categoryId = produit.getId_category();
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);

        if (categoryOptional.isEmpty()) {
            return "La catégorie du produit n'existe pas.";
        }

        // Associez la catégorie trouvée au produit
        produit.setId_category(categoryOptional.get().getId());

        // Récupérer l'utilisateur connecté
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return "Utilisateur non authentifié.";
        }
        String username = authentication.getName();
        Utilisateur utilisateur = utilisateurRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        produit.setUtilisateur(utilisateur);

        // Créer le produit
        Produit p = produitRepository.save(produit);

        // Créer une entrée dans EntreeSorti
        EntreeSorti es = new EntreeSorti();
        es.setProduit(p);
        es.setDate(new Date());
        es.setLibelle("Entrée");
        es.setQuantite(p.getQuantite());
        entreeSortiServiceImp.creer(es);

        // Ajouter au stock
        stockService.ajouterProduit(p);

        // Ajouter un historique
        historiqueService.addMODIFICATIONhistorique(p.getUtilisateur(), "Produit(id:" + p.getId() + ")");

        return "Produit ajouté avec succès!";
    }

    @Override
    @Transactional
    public String modifierProduit(Long id, Produit produitDetails) {
        return produitRepository.findById(id)
                .map(produit -> {
                    produit.setLibelle(produitDetails.getLibelle());
                    produit.setDescription(produitDetails.getDescription());
                    produit.setPrix(produitDetails.getPrix());
                    produit.setQuantite(produitDetails.getQuantite());
                    Produit p = produitRepository.save(produit);
                    EntreeSorti es = new EntreeSorti();
                    es.setProduit(p);
                    es.setDate(new Date());
                    es.setLibelle("Modification");
                    es.setQuantite(p.getQuantite());
                    entreeSortiServiceImp.creer(es);
                    stockService.ajouterProduit(p);
                    historiqueService.addMODIFICATIONhistorique(p.getUtilisateur(), "Produit(id:" + p.getId() + ") modifier avec succes");
                    return "Produit modifié avec succès!";
                }).orElseThrow(() -> new RuntimeException("Produit non trouvé"));
    }

    @Override
    @Transactional
    public String ajouterQuantiteProduit(Long id, int quantiteToAdd) {
        return produitRepository.findById(id)
                .map(produit -> {
                    produit.setQuantite(produit.getQuantite() + quantiteToAdd);
                    Produit p =  produitRepository.save(produit);
                    EntreeSorti es = new EntreeSorti();
                    es.setProduit(p);
                    es.setDate(new Date());
                    es.setLibelle("Ajout de quantite");
                    es.setQuantite(p.getQuantite());
                    entreeSortiServiceImp.creer(es);
                    stockService.ajouterProduit(p);
                    historiqueService.addCREATIONhistorique(p.getUtilisateur(), "Produit(id:" + p.getId() + ")quantite ajouté avec succès");
                    return "Quantité mise à jour avec succès!";
                }).orElseThrow(() -> new RuntimeException("Produit non trouvé"));
    }

    @Override
    @Transactional
    public String supprimerProduit(Long id) {
        // Trouver le produit par son ID
        Optional<Produit> optionalProduit = produitRepository.findById(id);

        if (!optionalProduit.isPresent()) {
            throw new RuntimeException("Produit non trouvé");
        }

        Produit produit = optionalProduit.get();

        // Récupérer l'utilisateur connecté
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Utilisateur non authentifié.");
        }
        String username = authentication.getName();
        Utilisateur utilisateur = utilisateurRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // Ajouter un historique de suppression
        historiqueService.addSUPPRESSIONhistorique(utilisateur, "Produit(id:" + produit.getId() + ")");

        // Supprimer le produit
        produitRepository.deleteById(id);

        return "Produit supprimé avec succès!";
    }


    @Override
    public List<Produit> lireProduits() {
        return produitRepository.findAll();
    }


    @Override
    public Variante ajouterVariante(Long produitId, Variante variante) {
        Produit produit = produitRepository.findById(produitId).orElseThrow(() -> new RuntimeException("Produit non trouvé"));
        variante.setProduit(produit);
        return varianteRepository.save(variante);
    }

    @Override
    public List<Variante> lireVariantes(Long produitId) {
        Produit produit = produitRepository.findById(produitId).orElseThrow(() -> new RuntimeException("Produit non trouvé"));
        return produit.getVariantes();
    }


}
