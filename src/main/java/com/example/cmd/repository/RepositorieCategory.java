package com.example.cmd.repository;

import com.example.cmd.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepositorieCategory extends JpaRepository<Category, Long> {

}
