package com.example.cmd.service;

import com.example.cmd.model.SousCategory;
import com.example.cmd.repository.SousCategoryRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
@Data
public class ImplementSousCate implements  SousCateService{
    @Autowired
    private SousCategoryRepository sousCategoryRepository;
    @Override
    public SousCategory ajouterSousCate( SousCategory sousCategory) {
        return sousCategoryRepository.save(sousCategory);

    }

    @Override
    public List<SousCategory> listerSousCate() {
        return sousCategoryRepository.findAll();
    }

    @Override
    public SousCategory modifierSousCate(SousCategory sousCategory, Long id) {
        return sousCategoryRepository.findById(id)
                .map(s->{
                    s.setName(sousCategory.getName());
                    sousCategoryRepository.save(s);
                    s.setCategory(sousCategory.getCategory());
                    return sousCategoryRepository.save(s);
                }).orElseThrow(() -> new RuntimeException("not found"));
    }

    @Override
    public String supprimerSousCate(Long id) {
        sousCategoryRepository.deleteById(id);
        return "SousCatégory supprimer";
    }
}
