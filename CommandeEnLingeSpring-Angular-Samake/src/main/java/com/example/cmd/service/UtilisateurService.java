package com.example.cmd.service;

import com.example.cmd.model.*;

import java.util.List;

public interface UtilisateurService {

    String ajouterPersonnel(Personnel personnel);
    String ajouterClient(Client client, String currentUserRole);
    String creerCompteClient(Client client);
    String ajouterAdmin(Admin admin);
    String modifiermotDePasse(String usemane, String NouveaumotDePasse);
    String modifierusername(Long id, String username);

    String modifierAdmin(Long id, Admin adminDetails);

    String modifierPersonnel(Long id, Personnel personnel);

    String ajouterProduit(ProductDto produit);

    List<ProductDto> lireProduit();

    String supprimerProduit(Long id);

    String modifierProduct(Long id, ProductDto productDetails);
    List<Utilisateur> lireUtilisateurs();

    String supprimerUtilisateur(Long id);

    String assignerCommandeALivreur(Long commandeId, Long livreurId);
    String ajouterRoleType(RoleType roleType);
    String modifierRoleType(Long id, RoleType roleTypeDetails);
    String supprimerRoleType(Long id);
    List<RoleType> lireRoleTypes();
}
