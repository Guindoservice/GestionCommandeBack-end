package com.example.cmd.controller;
import com.example.cmd.DTO.ChangePasswordDto;
import com.example.cmd.DTO.CreateClientDto;
import com.example.cmd.model.Client;
import com.example.cmd.model.StatusCompte;
import com.example.cmd.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    // Autres méthodes...

    @GetMapping("/{clientId}/profil")
    public Object afficherProfil(@PathVariable Long clientId) {
        if (!clientService.estCompteActif(clientId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Accès refusé : Compte désactivé.");
        }
        return "Votre compte est activé";
    }

    @PostMapping("/{clientId}/change-password")
    public ResponseEntity<?> changePassword(@PathVariable Long clientId, @RequestBody ChangePasswordDto changePasswordDto) {
        try {
            clientService.changePassword(clientId, changePasswordDto);
            return ResponseEntity.ok().build(); // Retourne une réponse HTTP 200 OK si le changement de mot de passe réussit
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // Retourne une réponse HTTP 400 Bad Request si l'ancien mot de passe est incorrect
        }
    }


    private Client convertirDtoEnEntite(CreateClientDto dto) {
        Client client = new Client();
        client.setNom(dto.getNom());
        client.setPrenom(dto.getPrenom());
        client.setEmail(dto.getEmail());
        client.setMotDePasse(dto.getMotDePasse()); // Assurez-vous de hasher le mot de passe avant de l'enregistrer
        client.setAdresse(dto.getAdresse());
        client.setTelephone(dto.getTelephone());
        client.setStatus(StatusCompte.ACTIVE); // Définissez le statut par défaut comme actif
        return client;
    }

}
