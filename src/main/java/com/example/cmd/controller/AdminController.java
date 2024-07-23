package com.example.cmd.controller;

import com.example.cmd.model.*;
import com.example.cmd.service.CategoryService;
import com.example.cmd.service.SousCateService;
import com.example.cmd.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")

public class AdminController {
    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private CategoryService categoryService;

    private Utilisateur utilisateur;
    @Autowired
    private SousCateService sousCateService;
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



    // Ajouter les sous catégory

    @PostMapping("/addSousCat")
    public SousCategory ajouterSousCategory(SousCategory sousCategory){
       return sousCateService.ajouterSousCate(sousCategory) ;

    }
    @GetMapping("/lireSousCat")
        public List<SousCategory> lireSousCat() {
           return null;
        }

        @PutMapping("/modifierSousCat/{id}")
        public SousCategory modifierSousCat(@PathVariable Long id, @RequestBody SousCategory sousCategory) {
      return null;
        }
        @DeleteMapping("/SUpprimerSouqCat")
        public String supprimerSouqCat(@PathVariable Long id, @RequestBody SousCategory sousCategory ) {
     return null;
        }

    // pour ajouter les catégory

    @PostMapping("/addCategory")
    public String ajouterCategory(@RequestBody Category category) {
        return categoryService.ajouteCategory(category);
    }
    @GetMapping("/lirecategory")
    public List<Category> lireCategory() {
        return categoryService.lireCategory();
    }

    @PutMapping("/modifierCategory/{id}")
    public Category modifierCategory(@PathVariable Long id , @RequestBody Category category) {
        return categoryService.modifierCategory(category,id);
    }
    @DeleteMapping("/supprimerCategory/{id}")
    public String supprimerCategory(@PathVariable Long id) {
        return categoryService.supprimerCategory(id);
    }


}
