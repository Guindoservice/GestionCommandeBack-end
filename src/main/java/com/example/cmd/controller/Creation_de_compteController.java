package com.example.cmd.controller;

import com.example.cmd.model.Client;
import com.example.cmd.service.UtilisateurService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/creation")




public class Creation_de_compteController {
    private final UtilisateurService utilisateurService;

    @PostMapping("/creer-compte-client")
    public ResponseEntity<String> creerCompteClient(@RequestBody Client client) {
        // Vérifier si un utilisateur est déjà authentifié
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            // Si l'utilisateur est authentifié (non anonyme)
            return ResponseEntity.badRequest().body("Vous êtes déjà connecté en tant que " + authentication.getName() + ". Vous ne pouvez pas créer un autre compte.");
        }

        // Appeler le service pour créer le compte client
        String message = utilisateurService.creerCompteClient(client);

        return ResponseEntity.ok(message);
    }

}
