package com.Mail.Service;

import com.Mail.Model.Commande;
import com.Mail.Repository.CommandeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommandeServiceImpl implements CommandeService{
    private CommandeRepository commandeRepository;

    @Override
    public Commande Creercommande(Commande commande) {
        return commandeRepository.save(commande);
    }
}
