package com.example.cmd.repository;

import com.example.cmd.model.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategorieRepository extends JpaRepository<Categorie, Long> {
}
