package com.example.cmd.repository;

import com.example.cmd.model.Produit;
import com.example.cmd.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProduitRepository extends JpaRepository<Produit, Long> {
   // List<Produit> findByUtilisateur(Utilisateur utilisateur);
}