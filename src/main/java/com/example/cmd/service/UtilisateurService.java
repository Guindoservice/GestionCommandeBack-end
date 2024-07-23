package com.example.cmd.service;

import com.example.cmd.model.*;

import java.util.List;//Admin ajouterAdmin(Admin admin);



public interface UtilisateurService {
String ajouterPersonnel(Personnel personnel);
String ajouterClient(Client client);


String modifierAdmin(Long id, Admin adminDetails);

String modifierPersonnel(Long id, Personnel personnel);

List<Utilisateur> lireUtilisateurs();

String supprimerUtilisateur(Long id);

String ajouterProduit(Produit produit);
String modifierProduit(Long id, Produit produitDetails);

List<Produit> lireProduit();

String supprimerProduit(Long id);



        String ajouterRoleType(RoleType roleType);
        String modifierRoleType(Long id, RoleType roleTypeDetails);
        String supprimerRoleType(Long id);
        List<RoleType> lireRoleTypes();


}