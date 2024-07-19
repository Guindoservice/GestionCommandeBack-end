package com.example.cmd.controller;

import com.example.cmd.model.*;
import com.example.cmd.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UtilisateurService utilisateurService;

    private Utilisateur utilisateur;

    @PostMapping("/creerpersonnel")
    public String ajouterPersonnel(@RequestBody Personnel personnel) {
        return utilisateurService.ajouterPersonnel(personnel);
    }

    @PostMapping("/creerclient")
    public String ajouterClient(@RequestBody Client client) {
        return utilisateurService.ajouterClient(client);
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
