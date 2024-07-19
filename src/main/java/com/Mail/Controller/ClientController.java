package com.Mail.Controller;


import com.Mail.Model.Client;
import com.Mail.Model.Commande;
import com.Mail.Service.ClientService;
import com.Mail.Service.CommandeService;
import lombok.AllArgsConstructor;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController("/Client")
@AllArgsConstructor
public class ClientController {

    private ClientService clientService;
    private CommandeService commandeService;
    private MailSender mailSender;


    @PostMapping("/Cree_client")
    public Client Cree_client (@RequestBody Client client){
        return clientService.Creerclient(client);
    }
    @PostMapping("/¨PasserCommande")
    public Commande Passe_commande(Commande commande){
        SimpleMailMessage message = new SimpleMailMessage();
        // Ici doit etre recuper l'email de celui qui est connecté message.setTo(utilisateur-connecter.getEmail());
        message.setSubject("Veuillez vérifier votre compte");
        message.setText("Bonjour, votre commande a bien été envoyer merci !!!!");
        mailSender.send(message);
        return commandeService.Creercommande(commande);
    }

}
