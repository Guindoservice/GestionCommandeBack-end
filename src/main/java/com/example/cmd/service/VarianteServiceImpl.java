package com.example.cmd.service;

import com.example.cmd.model.Variante;
import com.example.cmd.repository.VarianteRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class VarianteServiceImpl implements VarianteService {


    private  final VarianteRepository varianteRepository;

    @Override
    public List<Variante> varianteListe() {
        return varianteRepository.findAll();
    }

    @Override
    public Optional<Variante> trouverParId(Long id) {
        return varianteRepository.findById(id);
    }

    @Override
    public Variante ajouterVariante(Variante variante) {
        return varianteRepository.save(variante);
    }

    @Override
    public Variante modifierVariante(Long id, Variante variante) {
        if (varianteRepository.existsById(id)) {
            variante.setId(id);
            return varianteRepository.save(variante);
        } else {
            throw new EntityNotFoundException("Variante non trouvée avec l'id : " + id);
        }
    }

    @Override
    public void supprimerVariante(Long id) {
        if (varianteRepository.existsById(id)) {
            varianteRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Variante non trouvée avec l'id : " + id);
        }
    }
}
