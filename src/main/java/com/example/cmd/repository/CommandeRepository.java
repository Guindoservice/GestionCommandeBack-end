package com.example.cmd.repository;
import com.example.cmd.model.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommandeRepository extends JpaRepository<Commande, Long> {
    List<Commande> findByDeliverer_Id(Long delivererId);
    List<Commande> findByClient_Id(Long clientId);
    List<Commande> findByStatus(String status);

    List<Commande> findByUtilisateurId(Long clientId);
}
