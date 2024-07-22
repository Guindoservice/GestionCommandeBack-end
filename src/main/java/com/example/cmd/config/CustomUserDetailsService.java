package com.example.cmd.config;

import com.example.cmd.model.Client;
import com.example.cmd.model.Utilisateur;
import com.example.cmd.repository.ClientRepository;
import com.example.cmd.repository.UtilisateurRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UtilisateurRepository utilisateurRepository;
    private final ClientRepository clientRepository;

    public CustomUserDetailsService(UtilisateurRepository utilisateurRepository, ClientRepository clientRepository) {
        this.utilisateurRepository = utilisateurRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findByUsername(username);

        if (utilisateurOptional.isPresent()) {
            Utilisateur utilisateur = utilisateurOptional.get();
            return User.builder()
                    .username(utilisateur.getUsername())
                    .password(utilisateur.getMotDePasse())
                    .authorities("ROLE_" + utilisateur.getRoleType().getNom().toUpperCase())
                    .build();
        } else {
            Client client = clientRepository.findByUsername(username);
            if (client != null) {
                return User.builder()
                        .username(client.getUsername())
                        .password(client.getMotDePasse())
                        .authorities("ROLE_" + client.getRoleType().getNom().toUpperCase())
                        .build();
            } else {
                throw new UsernameNotFoundException("User not found with username: " + username);
            }
        }
    }
}
