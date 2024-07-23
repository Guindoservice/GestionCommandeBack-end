package com.example.cmd.service;

import com.example.cmd.model.*;
import com.example.cmd.repository.*;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@Setter
@Service
@AllArgsConstructor
public class UtilisateurServiceImpl implements UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ProduitRepository produitRepository;
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
    public String ajouterPersonnel(Personnel personnel) {
        // personnel.setRoleType(RoleType.PERSONNEL);
        personnel.setMotDePasse(passwordEncoder.encode(personnel.getMotDePasse()));
        utilisateurRepository.save(personnel);
        return "Nouveau formateur ajouté avec succès!";
    }

    @Transactional
    @Override
    public String ajouterClient(Client client) {
        //client.setRoleType(RoleType.CLIENT);
        client.setMotDePasse(passwordEncoder.encode(client.getMotDePasse()));
        utilisateurRepository.save(client);
        return "Nouveau client ajouté avec succès!";
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

