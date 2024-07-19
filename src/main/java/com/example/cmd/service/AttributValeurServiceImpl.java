package com.example.cmd.service;
import com.example.cmd.model.AttributValeur;
import com.example.cmd.repository.AttributValeurRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AttributValeurServiceImpl implements AttributValeurService {

    private final AttributValeurRepository attributValeurRepository;
    @Override
    public List<AttributValeur> attributValeurListe() {
        return attributValeurRepository.findAll();
    }

    @Override
    public Optional<AttributValeur> trouverParId(Long id) {
        return attributValeurRepository.findById(id);
    }

    @Override
    public AttributValeur ajouterAttributValeur(AttributValeur attributValeur) {
        return attributValeurRepository.save(attributValeur);
    }

    @Override
    public AttributValeur modifierAttributValeur(Long id, AttributValeur attributValeur) {
        if (attributValeurRepository.existsById(id)) {
            attributValeur.setId(id);
            return attributValeurRepository.save(attributValeur);
        } else {
            throw new EntityNotFoundException("AttributValeur non trouvé avec l'id : " + id);
        }
    }

    @Override
    public void supprimerAttributValeur(Long id) {
        if (attributValeurRepository.existsById(id)) {
            attributValeurRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("AttributValeur non trouvé avec l'id : " + id);
        }
    }

}
