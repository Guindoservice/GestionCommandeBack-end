package com.example.cmd.controller;

import com.example.cmd.model.Commande;
import com.example.cmd.model.ProductDto;
import com.example.cmd.service.CommandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/commandes")
public class CommandeController {

    @Autowired
    private CommandeService commandeService;

    @GetMapping
    public List<Commande> getAllCommandes() {
        return commandeService.getAllCommandes();
    }

    @GetMapping("/{id}")
    public Commande getCommandeById(@PathVariable Long id) {
        return commandeService.getCommandeById(id);
    }

    @PostMapping
    public Commande createCommande(@RequestBody Commande commande) {
        return commandeService.saveCommande(commande);
    }

    @DeleteMapping("/{id}")
    public void deleteCommande(@PathVariable Long id) {
        commandeService.deleteCommande(id);
    }

    @GetMapping("/deliverer/{delivererId}")
    public List<Commande> getCommandesByDelivererId(@PathVariable Long delivererId) {
        return commandeService.getCommandesByDelivererId(delivererId);
    }

    // Endpoint pour calculer le montant total des commandes d'un client
    @PostMapping("/calculer-montant/{clientId}")
    public BigDecimal calculerMontantTotalCommandesClient(@PathVariable Long clientId, @RequestBody List<ProductDto> products) {
        return commandeService.calculerMontantTotalCommandesClient(clientId, products);
    }

    // Endpoint pour effectuer le paiement d'une commande
    @PostMapping("/payer/{clientId}")
    public String payerCommande(@PathVariable Long clientId, @RequestBody List<ProductDto> products) {
        return commandeService.payerCommande(clientId, products);
    }

    // Endpoint pour assigner une commande à un livreur
    @PostMapping("/assigner/{commandeId}/{livreurId}")
    public String assignCommandToDeliverer(@PathVariable Long commandeId, @PathVariable Long livreurId) {
        return commandeService.assignCommandToDeliverer(commandeId, livreurId);
    }
}
