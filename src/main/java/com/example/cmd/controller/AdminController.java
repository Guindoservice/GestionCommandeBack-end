package com.example.cmd.controller;

import com.example.cmd.model.*;
import com.example.cmd.repository.CategoryRepository;
import com.example.cmd.repository.UtilisateurRepository;
import com.example.cmd.service.CategoryService;
import com.example.cmd.service.ProduitService;
import com.example.cmd.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private JavaMailSender mailSender;

    private Utilisateur utilisateur;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProduitService produitService;

    // Endpoints pour les rôles

    @GetMapping("/checkRole")
    public ResponseEntity<?> checkRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(authentication.getAuthorities());
    }

    @PostMapping("/desactiver-compte/{clientId}")
    public String desactiverCompteClient(@PathVariable Long clientId) {
        utilisateurService.desactiverCompteClient(clientId);
        return "Compte désactivé avec succès.";
    }

    @PostMapping("/roles")
    public ResponseEntity<String> ajouterRole(@RequestBody RoleType roleType) {
        String message = utilisateurService.ajouterRoleType(roleType);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @PutMapping("/roles/{id}")
    public ResponseEntity<String> modifierRole(@PathVariable Long id, @RequestBody RoleType roleTypeDetails) {
        String message = utilisateurService.modifierRoleType(id, roleTypeDetails);
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/roles/{id}")
    public ResponseEntity<String> supprimerRole(@PathVariable Long id) {
        String message = utilisateurService.supprimerRoleType(id);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/roles")
    public ResponseEntity<List<RoleType>> lireRoles() {
        List<RoleType> roles = utilisateurService.lireRoleTypes();
        return ResponseEntity.ok(roles);
    }

    // Endpoints pour les utilisateurs


    @PostMapping("/creeradmin")
    public ResponseEntity<String> ajouterAdmin(@RequestBody Admin admin, Authentication authentication) {
        String currentUserRole = authentication.getAuthorities().iterator().next().getAuthority();
        if (!currentUserRole.equals("ROLE_ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Vous n'avez pas les droits nécessaires pour cette opération.");
        }
        String message = utilisateurService.ajouterAdmin(admin);
        return ResponseEntity.ok(message);
    }

    @PutMapping("/modifieradmin/{id}")
    public ResponseEntity<String> modifierAdmin(@PathVariable Long id, @RequestBody Admin admin) {
        String message = utilisateurService.modifierAdmin(id, admin);
        return ResponseEntity.ok(message);
    }

    @PutMapping("/modifierMotDePasse")
    public ResponseEntity<String> modifierMotDePasse(@RequestBody Map<String, String> requestBody) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            String nouveauMotDePasse = requestBody.get("nouveauMotDePasse");
            if (nouveauMotDePasse == null || nouveauMotDePasse.isEmpty()) {
                return ResponseEntity.badRequest().body("Le nouveau mot de passe ne peut pas être vide.");
            }
            String message = utilisateurService.modifiermotDePasse(username, nouveauMotDePasse);
            return ResponseEntity.ok(message);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/Creerpersonnel")
    public ResponseEntity<Personnel> Creerpersonnel(@RequestBody Personnel personnel) {
        try {
            // Récupérer l'authentification pour obtenir l'administrateur courant
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String adminUsername = authentication.getName();
            System.out.println("Nom d'utilisateur authentifié : " + adminUsername);

            // Vérifier que l'utilisateur est un admin
            Admin admin = utilisateurService.findAdminByUsername(adminUsername)
                    .orElseThrow(() -> new RuntimeException("Administrateur non trouvé"));

            // Assigner l'administrateur au personnel
            personnel.setAdmin(admin);

            // Vérifiez que l'email est présent
            if (personnel.getEmail() == null || personnel.getEmail().isEmpty()) {
                throw new IllegalArgumentException("L'email ne peut pas être vide.");
            }

            // Créer le personnel
            Personnel createdPersonnel = (Personnel) utilisateurService.ajouterPersonnel(personnel);

            System.out.println("Email du personnel créé : " + createdPersonnel.getEmail());

            // Envoi de l'e-mail de confirmation
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(createdPersonnel.getEmail());
            message.setSubject("Compte créé avec succès");
            message.setText("Bonjour, votre compte a été créé avec succès. Vos identifiants sont:\nUsername: " + createdPersonnel.getUsername() + "\nMot de passe: " + createdPersonnel.getMotDePasse());
            mailSender.send(message);

            return ResponseEntity.ok(createdPersonnel);
        } catch (Exception e) {
            // Log l'erreur pour faciliter le débogage
            System.err.println("Erreur lors de la création du personnel et de l'envoi de l'email : " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    @PutMapping("/modifierpersonnel/{id}")
    public ResponseEntity<String> modifierPersonnel(@PathVariable Long id, @RequestBody Personnel personnel) {
        String message = utilisateurService.modifierPersonnel(id, personnel);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/listutilisateurs")
    public ResponseEntity<List<Utilisateur>> lireUtilisateurs() {
        List<Utilisateur> utilisateurs = utilisateurService.lireUtilisateurs();
        return ResponseEntity.ok(utilisateurs);
    }

    @DeleteMapping("/supprimerutilisateur/{id}")
    public ResponseEntity<String> supprimerUtilisateur(@PathVariable Long id) {
        String message = utilisateurService.supprimerUtilisateur(id);
        return ResponseEntity.ok(message);
    }

    // Endpoints pour les catégories

    @PostMapping("/categories")
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        Category createdCategory = categoryService.createCategory(category.getName());
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        return new ResponseEntity<>(categoryService.getAllCategories(), HttpStatus.OK);
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable Long id) {
        Category category = categoryService.getCategory(id);
        if (category != null) {
            return new ResponseEntity<>(category, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        Category updatedCategory = categoryService.updateCategory(id, category.getName());
        if (updatedCategory != null) {
            return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>("Catégorie supprimée avec succès!", HttpStatus.OK);
    }

    @PostMapping(value = "/Creerproduit", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> ajouterProduit(@RequestBody Produit produit) {
        try {
            // Récupérer l'utilisateur connecté
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Utilisateur non authentifié.");
            }

            String username = authentication.getName();
            Utilisateur utilisateur = utilisateurRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

            produit.setUtilisateur(utilisateur);

            // Ajouter le produit
            String resultat = (String) produitService.ajouterProduit(produit);

            return ResponseEntity.ok(resultat);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'ajout du produit: " + e.getMessage());
        }
    }
    }






