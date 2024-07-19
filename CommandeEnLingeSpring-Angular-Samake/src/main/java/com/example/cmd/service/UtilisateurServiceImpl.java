package com.example.cmd.service;

import com.example.cmd.model.*;
import com.example.cmd.repository.CommandeRepository;
import com.example.cmd.repository.ProductRepository;
import com.example.cmd.repository.RoleRepository;
import com.example.cmd.repository.UtilisateurRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UtilisateurServiceImpl implements UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final CommandeRepository commandeRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ProductRepository productRepository;
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
        Optional<RoleType> roleTypeOptional = roleRepository.findById(id);

        if (roleTypeOptional.isPresent()) {
            roleRepository.deleteById(id);
            return "Role supprimé avec succès!";
        } else {
            return "Aucun role trouvé avec l'id fourni.";
        }
    }


    @Override
    public List<RoleType> lireRoleTypes() {
        return roleRepository.findAll();
    }

    @Override
    public String ajouterPersonnel(Personnel personnel) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            throw new RuntimeException("Utilisateur non authentifié");
        }

        String adminUsername = authentication.getName();
        Utilisateur admin = utilisateurRepository.findByUsername(adminUsername)
                .orElseThrow(() -> new RuntimeException("Administrateur non trouvé avec le nom d'utilisateur : " + adminUsername));

        try {
            RoleType personnelRole = roleRepository.findByNom("PERSONNEL")
                    .orElseGet(() -> roleRepository.save(new RoleType("PERSONNEL")));
            personnel.setRoleType(personnelRole);
            personnel.setMotDePasse(passwordEncoder.encode(personnel.getMotDePasse()));
            personnel.setAdmin((Admin) admin);

            utilisateurRepository.save(personnel);
            return "Nouveau personnel ajouté avec succès!";
        } catch (Exception e) {
            return "Erreur lors de l'ajout du personnel : " + e.getMessage();
        }
    }

    @Override
    public String modifierPersonnel(Long id, Personnel personnelDetails) {
        return utilisateurRepository.findById(id)
                .map(personnel -> {
                    personnel.setUsername(personnelDetails.getUsername());
                    personnel.setMotDePasse(passwordEncoder.encode(personnelDetails.getMotDePasse()));
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
        Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findById(id);

        if (utilisateurOptional.isPresent()) {
            utilisateurRepository.deleteById(id);
            return "Utilisateur supprimé avec succès!";
        } else {
            return "Aucun utilisateur trouvé avec l'id fourni.";
        }
    }


    @Transactional
    @Override
    public String ajouterClient(Client client, String currentUserRole) {
        if (!"ADMIN".equals(currentUserRole)) {
            return "Vous n'êtes pas autorisé à ajouter un client";
        }

        RoleType clientRole = roleRepository.findByNom("CLIENT")
                .orElseGet(() -> roleRepository.save(new RoleType("CLIENT")));
        client.setRoleType(clientRole);
        client.setMotDePasse(passwordEncoder.encode(client.getMotDePasse()));
        utilisateurRepository.save(client);

        return "Nouveau client ajouté avec succès!";
    }

    @Override
    public String creerCompteClient(Client client) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            return "Vous êtes déjà connecté en tant que " + authentication.getName() + ". Vous ne pouvez pas créer un autre compte.";
        }

        try {
            RoleType clientRole = roleRepository.findByNom("CLIENT")
                    .orElseGet(() -> roleRepository.save(new RoleType("CLIENT")));
            client.setRoleType(clientRole);
            client.setMotDePasse(passwordEncoder.encode(client.getMotDePasse()));
            utilisateurRepository.save(client);
            return "Compte client créé avec succès pour " + client.getUsername();
        } catch (Exception e) {
            return "Erreur lors de la création du compte client : " + e.getMessage();
        }
    }

    @Override
    public String ajouterAdmin(Admin admin) {
        RoleType adminRole = roleRepository.findByNom("ADMIN")
                .orElseGet(() -> roleRepository.save(new RoleType("ADMIN")));
        admin.setRoleType(adminRole);
        admin.setMotDePasse(passwordEncoder.encode(admin.getMotDePasse()));
        utilisateurRepository.save(admin);
        return "Nouvel admin ajouté avec succès!";
    }

    @Override
    public String modifiermotDePasse(String username, String NouveaumotDePasse) {
        Utilisateur utilisateur = utilisateurRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé: " + username));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getName().equals(username)) {
            throw new IllegalStateException("Vous n'êtes pas autorisé à modifier ce mot de passe");
        }

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
        utilisateurRepository.save(admin);
        return "Admin modifié avec succès!";
    }



    @Override
    public String ajouterProduit(ProductDto produit) {
        return "";
    }

    @Override
    public List<ProductDto> lireProduit() {
        return List.of();
    }

    @Override
    public String supprimerProduit(Long id) {
        return "";
    }

    @Override
    public String modifierProduct(Long id, ProductDto productDetails) {
        return "";
    }



    @Override
    public String assignerCommandeALivreur(Long commandeId, Long livreurId) {
        return "";
    }

    @PostConstruct
    public void initAdmin() {
        RoleType adminRole = roleRepository.findByNom("ADMIN")
                .orElseGet(() -> roleRepository.save(new RoleType("ADMIN")));

        List<Utilisateur> admins = utilisateurRepository.findByRoleType(adminRole);

        if (admins.isEmpty()) {
            Admin admin = new Admin("samake", passwordEncoder.encode("samake"), adminRole);
            utilisateurRepository.save(admin);
        }
    }
}
