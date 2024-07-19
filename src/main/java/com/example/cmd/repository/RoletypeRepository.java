package com.example.cmd.repository;

import com.example.cmd.model.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoletypeRepository extends JpaRepository<RoleType, Long> {
    RoleType findByNom(String nom);
}
