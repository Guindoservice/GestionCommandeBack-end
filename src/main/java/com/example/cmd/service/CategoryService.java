package com.example.cmd.service;

import com.example.cmd.model.Category;
import com.example.cmd.model.SousCategory;

import java.util.List;

public interface CategoryService {
    // categorie et sous categorie
    String ajouteCategory(Category category);
    Category modifierCategory(Category category, Long id);
    String supprimerCategory(Long id);
    List<Category> lireCategory();

}
