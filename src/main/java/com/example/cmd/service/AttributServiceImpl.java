package com.example.cmd.service;

import com.example.cmd.model.Attribut;
import com.example.cmd.repository.AttributRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AttributServiceImpl implements AttributService {

    private final AttributRepository attributRepository;

    @Override
    public List<Attribut> attributListe() {
        return attributRepository.findAll();
    }

    @Override
    public Optional<Attribut> trouverParId(Long id) {
        return attributRepository.findById(id);
    }

    @Override
    public Attribut ajouterAttribut(Attribut attribut) {
        return attributRepository.save(attribut);
    }

    @Override
    public Attribut modifierAttribut(Long id, Attribut attribut) {
        if (attributRepository.existsById(id)) {
            attribut.setId(id);
            return attributRepository.save(attribut);
        } else {
            throw new EntityNotFoundException("Attribut non trouvé avec l'id : " + id);
        }
    }

    @Override
    public void supprimerAttribut(Long id) {
        if (attributRepository.existsById(id)) {
            attributRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Attribut non trouvé avec l'id : " + id);
        }
    }
}
