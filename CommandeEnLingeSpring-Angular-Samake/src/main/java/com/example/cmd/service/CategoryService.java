package com.example.cmd.service;

import com.example.cmd.model.Category;
import com.example.cmd.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategory(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public Category getCategory(String name) {
        return categoryRepository.findByName(name);
    }

    public Category createCategory(String name) {
        Category category = new Category(name);
        return categoryRepository.save(category);
    }

    public Category updateCategory(Long id, String name) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category != null) {
            category.setName(name);
            return categoryRepository.save(category);
        }
        return null;
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
