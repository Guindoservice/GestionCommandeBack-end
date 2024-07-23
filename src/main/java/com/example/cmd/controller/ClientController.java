package com.example.cmd.controller;

import com.example.cmd.model.Client;
import com.example.cmd.service.UtilisateurService;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@RestController
@AllArgsConstructor
@RequestMapping("/client")



public class ClientController {


   private final UtilisateurService utilisateurService;

    @PostMapping("/creerclient")
    public String ajouterClient(@RequestBody Client client) {
    return utilisateurService.ajouterClient(client);
    }
}