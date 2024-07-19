package com.example.cmd.controller;

import com.example.cmd.model.*;
import com.example.cmd.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private Utilisateur utilisateur;

    @PostMapping("/creeradmin")
    public String ajouterAdmin(@RequestBody Admin admin) {
        return utilisateurService.ajouterAdmin(admin);
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


    // Endpoint pour l'ajout d'un client (accessible uniquement aux administrateurs)
    @PostMapping("/ajouterClient")
    public ResponseEntity<String> ajouterClient(@RequestBody Client client, Authentication authentication) {
        // Obtenir le rôle de l'utilisateur connecté
        String currentUserRole = authentication.getAuthorities().iterator().next().getAuthority();

        String message = utilisateurService.ajouterClient(client, currentUserRole);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/creerpersonnel")
    public ResponseEntity<String> ajouterPersonnel(@RequestBody Personnel personnel) {
        String message = utilisateurService.ajouterPersonnel(personnel);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }




    @PutMapping("/modifieradmin/{id}")
    public String modifierAdmin(@PathVariable Long id, @RequestBody Admin admin) {
        return utilisateurService.modifierAdmin(id, admin);
    }

    @PutMapping("/modifierpersonnel/{id}")
    public String modifierPersonnel(@PathVariable Long id, @RequestBody Personnel personnel) {
        return utilisateurService.modifierPersonnel(id, personnel);
    }

    @GetMapping("/listutilisateurs")
    public List<Utilisateur> lireUtilisateurs() {
        return utilisateurService.lireUtilisateurs();
    }

    @DeleteMapping("/supprimerutilisateur/{id}")
    public String supprimerUtilisateur(@PathVariable Long id) {
        return utilisateurService.supprimerUtilisateur(id);
    }

    @PostMapping("/creerproduit")
    public String ajouterproduit(@RequestBody Produit produit) {
        //produit.setUtilisateur(utilisateur);
        return utilisateurService.ajouterProduit(produit);
    }

    @GetMapping("/listProduit")
    public List<Produit> lireProduit() {
        return utilisateurService.lireProduit();
    }

    @DeleteMapping("/supprimerProduit/{id}")
    public String supprimerProduit(@PathVariable Long id) {
        return utilisateurService.supprimerProduit(id);
    }

    @PutMapping("/modifierproduit/{id}")
    public String modifierProduit(@PathVariable Long id, @RequestBody Produit produit) {
        return utilisateurService.modifierProduit(id, produit);
    }

    @PostMapping("/creercategorie")
    public String ajouterCategorie(@RequestBody Categorie categorie) {
        return utilisateurService.ajouterCategorie(categorie);
    }

    @PutMapping("/modifiercategorie/{id}")
    public String modifierCategorie(@PathVariable Long id, @RequestBody Categorie categorie) {
        return utilisateurService.modifierCategorie(id, categorie);
    }

    @DeleteMapping("/supprimercategorie/{id}")
    public String supprimerCategorie(@PathVariable Long id) {
        return utilisateurService.supprimerCategorie(id);
    }

    @GetMapping("/listcategories")
    public List<Categorie> lireCategories() {
        return utilisateurService.lireCategories();
    }
    @PostMapping("/creerroletype")
    public String ajouterRoleType(@RequestBody RoleType roleType) {
        return utilisateurService.ajouterRoleType(roleType);
    }

    @PutMapping("/modifierroletype/{id}")
    public String modifierRoleType(@PathVariable Long id, @RequestBody RoleType roleTypeDetails) {
        return utilisateurService.modifierRoleType(id, roleTypeDetails);
    }

    @DeleteMapping("/supprimerroletype/{id}")
    public String supprimerRoleType(@PathVariable Long id) {
        return utilisateurService.supprimerRoleType(id);
    }

    @GetMapping("/listroletypes")
    public List<RoleType> lireRoleTypes() {
        return utilisateurService.lireRoleTypes();
    }
}
