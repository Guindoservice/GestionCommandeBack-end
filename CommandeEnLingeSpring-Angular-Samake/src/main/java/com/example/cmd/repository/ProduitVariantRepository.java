package com.example.cmd.repository;

import com.example.cmd.model.ProduitVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProduitVariantRepository extends JpaRepository<ProduitVariant, Long> {
    List<ProduitVariant> findByCategoryId(Long categoryId);
}
