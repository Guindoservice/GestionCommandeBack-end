package com.example.cmd.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("ALL")
@Data
@Entity
@Table(name = "products")
public class ProductDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private BigDecimal prix;
    private int quantite;

    @Transient
    private MultipartFile imageFile;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    LocalDate date = LocalDate.now();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Commande> commandes = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "id_utilisateur")
    private Utilisateur utilisateur;

    public ProductDto() { }

    public ProductDto(String name, String description, BigDecimal prix, Image img, int quantite, Category category) {
        this.name = name;
        this.description = description;
        this.prix = prix;
        this.quantite= quantite;
        this.imageFile = imageFile;
        this.category = category;
    }

    public Long getCategoryId() {
        return category != null ? category.getId() : null;
    }

    public Long getUtilisateurId() {
        return utilisateur != null ? utilisateur.getId() : null;
    }
}