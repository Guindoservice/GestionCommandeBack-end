package com.example.cmd.repository;

import com.example.cmd.model.Category;

import com.example.cmd.model.ProductDto;
import com.example.cmd.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ProductRepository extends JpaRepository<ProductDto, Long> {
    List<ProductDto> findByUtilisateur(Utilisateur utilisateur);
    Optional<ProductDto> findById(Long id);
    List<ProductDto> findByCategory(Category category);
    List<ProductDto> findByNameContaining(String name);
}