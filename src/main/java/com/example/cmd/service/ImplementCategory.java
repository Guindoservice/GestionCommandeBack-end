package com.example.cmd.service;

import com.example.cmd.model.Category;
import com.example.cmd.repository.RepositorieCategory;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Data
public class ImplementCategory implements  CategoryService{

    //@Autowired
    private final RepositorieCategory repositorieCategory;


    @Override
    public String ajouteCategory(Category category) {
        repositorieCategory.save(category);
        return "la catégorie a été  ajouté";
    }

    @Override
    public Category modifierCategory(Category category, Long id) {
        return repositorieCategory.findById(id)
                .map(a->{
                    a.setNom(category.getNom());
                    a.setDescription(category.getDescription());
                    a.setSousCategorys(category.getSousCategorys());
                    return  repositorieCategory.save(a);
                }) .orElseThrow(() -> new RuntimeException("pas de category "+ id) );
    }

    @Override
    public String supprimerCategory(Long id) {
        repositorieCategory.deleteById(id);

        return "La catégorie a été supprimer";
    }
    @Override
    public List<Category> lireCategory() {
        return repositorieCategory.findAll();
    }





}
