package com.example.cmd.service;

import com.example.cmd.model.*;
import com.example.cmd.repository.CategorieRepository;
import com.example.cmd.repository.ProduitRepository;
import com.example.cmd.repository.RoleRepository;
import com.example.cmd.repository.UtilisateurRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
@Service
@AllArgsConstructor
public class UtilisateurServiceImpl implements UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ProduitRepository produitRepository;
    private final CategorieRepository categorieRepository;
    private final RoleRepository roleRepository;


    @Override
    public String ajouterRoleType(RoleType roleType) {
        roleRepository.save(roleType);
        return "Role ajouté avec succès!";
    }

    @Override
    public String modifierRoleType(Long id, RoleType roleTypeDetails) {
        return roleRepository.findById(id)
                .map(roleType -> {
                    roleType.setNom(roleTypeDetails.getNom());
                    roleRepository.save(roleType);
                    return "Role modifié avec succès!";
                }).orElseThrow(() -> new RuntimeException("Role n'existe pas"));
    }






    @Override
    public String supprimerRoleType(Long id) {
        roleRepository.deleteById(id);
        return "Role supprimé avec succès!";
    }

    @Override
    public List<RoleType> lireRoleTypes() {
        return roleRepository.findAll();
    }

    @Override
    public String ajouterCategorie(Categorie categorie) {
        categorieRepository.save(categorie);
        return "Categorie ajoutée avec succès!";
    }

    @Override
    public String modifierCategorie(Long id, Categorie categorieDetails) {
        return categorieRepository.findById(id)
                .map(categorie -> {
                    categorie.setNom(categorieDetails.getNom());
                    categorieRepository.save(categorie);
                    return "Categorie modifiée avec succès!";
                }).orElseThrow(() -> new RuntimeException("Categorie n'existe pas"));
    }

    @Override
    public String supprimerCategorie(Long id) {
        categorieRepository.deleteById(id);
        return "Categorie supprimée avec succès!";
    }

    @Override
    public List<Categorie> lireCategories() {
        return categorieRepository.findAll();
    }




    @Override
    public String ajouterPersonnel(Personnel personnel) {
        // Récupérer les détails de l'administrateur actuellement authentifié
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String adminUsername = authentication.getName();

        // Rechercher l'administrateur dans la base de données par son nom d'utilisateur
        Utilisateur admin = utilisateurRepository.findByUsername(adminUsername)
                .orElseThrow(() -> new RuntimeException("Administrateur non trouvé avec le nom d'utilisateur : " + adminUsername));

        // Vérifier si le rôle PERSONNEL existe, sinon le créer
        try {
            RoleType personnelRole = roleRepository.findByNom("PERSONNEL")
                    .orElseGet(() -> roleRepository.save(new RoleType("PERSONNEL")));
            personnel.setRoleType(personnelRole);
            personnel.setMotDePasse(passwordEncoder.encode(personnel.getMotDePasse()));

            // Définir l'administrateur qui ajoute le personnel
            personnel.setAdmin((Admin) admin);

            utilisateurRepository.save(personnel);
            return "Nouveau personnel ajouté avec succès!";
        } catch (Exception e) {
            // Gérer les erreurs en cas de problème lors de l'ajout
            return "Erreur lors de l'ajout du personnel : " + e.getMessage();
        }

    }

    @Transactional
    @Override
    public String ajouterClient(Client client, String currentUserRole) {
        // Vérifier si l'utilisateur actuel a le rôle approprié pour ajouter un client
        if (!currentUserRole.equals("ADMIN")) {
            return "Vous n'êtes pas autorisé à ajouter un client";
        }

        // Vérifier si le rôle client existe, sinon le créer
        RoleType clientRole = roleRepository.findByNom("CLIENT")
                .orElseGet(() -> roleRepository.save(new RoleType("CLIENT")));
        client.setRoleType(clientRole);

        // Encoder le mot de passe avant de l'enregistrer
        client.setMotDePasse(passwordEncoder.encode(client.getMotDePasse()));
        utilisateurRepository.save(client);

        return "Nouveau client ajouté avec succès!";
    }

    @Override
    public String creerCompteClient(Client client) {
        // Vérifier si un utilisateur est déjà authentifié
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            // Si l'utilisateur est authentifié (non anonyme)
            return "Vous êtes déjà connecté en tant que " + authentication.getName() + ". Vous ne pouvez pas créer un autre compte.";
        }

        try {
            // Implémentez la logique pour créer un compte client ici
            // Vérifier si le rôle CLIENT existe, sinon le créer
            RoleType clientRole = roleRepository.findByNom("CLIENT")
                    .orElseGet(() -> roleRepository.save(new RoleType("CLIENT")));
            client.setRoleType(clientRole);

            // Encoder le mot de passe avant de l'enregistrer
            client.setMotDePasse(passwordEncoder.encode(client.getMotDePasse()));

            // Sauvegarder le client dans la base de données
            utilisateurRepository.save(client);

            return "Compte client créé avec succès pour " + client.getUsername();
        } catch (Exception e) {
            // Gérer les erreurs en cas de problème lors de la création du compte client
            return "Erreur lors de la création du compte client : " + e.getMessage();
        }
    }
    @Override
    public String ajouterAdmin(Admin admin) {
        // Vérifier si le rôle ADMIN existe, sinon le créer
        RoleType adminRole = roleRepository.findByNom("ADMIN")
                .orElseGet(() -> roleRepository.save(new RoleType("ADMIN")));
        admin.setRoleType(adminRole);
        admin.setMotDePasse(passwordEncoder.encode(admin.getMotDePasse()));
        utilisateurRepository.save(admin);
        return "Nouvel admin ajouté avec succès!";
    }

    @Override
    public String modifiermotDePasse(String username, String NouveaumotDePasse) {
        // Rechercher l'utilisateur dans la base de données
        Utilisateur utilisateur = utilisateurRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé: " + username));

        // Vérifier l'ancien mot de passe
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getName().equals(username)) {
            throw new IllegalStateException("Vous n'êtes pas autorisé à modifier ce mot de passe");
        }

        // Mettre à jour le mot de passe de l'utilisateur
        utilisateur.setMotDePasse(passwordEncoder.encode(NouveaumotDePasse));
        utilisateurRepository.save(utilisateur);

        return "Mot de passe mis à jour avec succès pour l'utilisateur: " + username;
    }

    @Override
    public String modifierusername(Long id, String username) {
        return utilisateurRepository.findById(id)
                .map(utilisateur -> {
                    utilisateur.setUsername(username);
                    utilisateurRepository.save(utilisateur);
                    return "Username modifié avec succès!";
                }).orElseThrow(() -> new RuntimeException("Utilisateur n'existe pas"));
    }

    @Override
    public String modifierAdmin(Long id, Admin adminDetails) {
        Admin admin = (Admin) utilisateurRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé avec id : " + id));

        admin.setUsername(adminDetails.getUsername());
        admin.setMotDePasse(passwordEncoder.encode(adminDetails.getMotDePasse()));
        //admin.setRoleType(RoleType.ADMIN);

        utilisateurRepository.save(admin);
        return "Admin modifié avec succès!";
    }

    @Override
    public String modifierPersonnel(Long id, Personnel personnelDetails) {
        return utilisateurRepository.findById(id)
                .map(personnel -> {
                    personnel.setUsername(personnelDetails.getUsername()); // Correction ici
                    personnel.setMotDePasse(passwordEncoder.encode(personnelDetails.getMotDePasse())); // Correction ici
                    utilisateurRepository.save(personnel);
                    return "Personnel modifié avec succès!";
                }).orElseThrow(() -> new RuntimeException("Personnel n'existe pas"));
    }

    @Transactional
    @Override
    public List<Utilisateur> lireUtilisateurs() {
        return utilisateurRepository.findAll();
    }

    @Transactional
    @Override
    public String supprimerUtilisateur(Long id) {
        utilisateurRepository.deleteById(id);
        return "Utilisateur supprimé!!!";
    }

    @Transactional
    @Override
    public String ajouterProduit(Produit produit) {
        produitRepository.save(produit);
        return "Produit ajouté avec succès!";
    }

    @Override
    public String modifierProduit(Long id, Produit produitDetails) {
        return produitRepository.findById(id)
                .map(produit -> {
                    produit.setNom(produitDetails.getNom());
                    produit.setPrix(produitDetails.getPrix());
                    produit.setDescription(produitDetails.getDescription());
                    produit.setQuantite(produitDetails.getQuantite());
                    produit.setCategorie(produitDetails.getCategorie());

                    produitRepository.save(produit);
                    return "Produit modifié avec succès!";
                }).orElseThrow(() -> new RuntimeException("Produit n'existe pas"));
    }

    @Override
    public List<Produit> lireProduit() {
        return produitRepository.findAll();
    }

    @Override
    public String supprimerProduit(Long id) {
        produitRepository.deleteById(id);
        return "Produit supprimé!!!";
    }

    @PostConstruct
    public void initAdmin() {
        // Vérifier si le rôle ADMIN existe, sinon le créer
        RoleType adminRole = roleRepository.findByNom("ADMIN")
                .orElseGet(() -> roleRepository.save(new RoleType("ADMIN")));

        // Rechercher les utilisateurs avec le rôle ADMIN
        List<Utilisateur> admins = utilisateurRepository.findByRoleType(adminRole);

        if (admins.isEmpty()) {
            Admin admin = new Admin("admin", passwordEncoder.encode("admin"), adminRole);
            utilisateurRepository.save(admin);
        }
    }
}

