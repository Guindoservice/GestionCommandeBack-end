package com.example.cmd.service;

import com.example.cmd.model.Category;
import com.example.cmd.model.ProduitVariant;
import com.example.cmd.repository.CategoryRepository;
import com.example.cmd.repository.ProduitVariantRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProduitVariantService {
    @Autowired
    private final ProduitVariantRepository productVariantRepository;
    @Autowired
    private  final CategoryRepository categoryRepository;

    public List<ProduitVariant> getVariantsByCategoryId(Long categoryId) {
        return productVariantRepository.findByCategoryId(categoryId);
    }

    public  ProduitVariant createProductVariant(String name, Long categoryId) {
        // Assurez-vous que la catégorie existe avant de créer une variante
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Catégorie non trouvée avec l'ID : " + categoryId));
        ProduitVariant variant = new ProduitVariant(name, category);
        return productVariantRepository.save(variant);
    }

}
