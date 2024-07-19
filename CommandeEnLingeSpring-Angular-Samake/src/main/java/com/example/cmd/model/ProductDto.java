package com.example.cmd.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Lob
    @Column(name = "image_data", columnDefinition = "LONGBLOB")
    private byte[] imageData;

    @Transient
    private MultipartFile imageFile;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "id_utilisateur")
    private Utilisateur utilisateur;

    private String imagePath;

    // Getters et setters pour categoryId et utilisateurId
    @Setter
    @Getter
    @Transient
    private Long categoryId;

    // Nouvelle propriété pour l'ID de l'utilisateur
    @Transient
    private Long utilisateurId;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Commande> commandes = new HashSet<>();

    @OneToMany(mappedBy = "produit")
    private List<Avis> avis;

    public ProductDto(String name, String description, BigDecimal prix, int quantite, Category category) {
        this.name = name;
        this.description = description;
        this.prix = prix;
        this.quantite = quantite;
        this.category = category;
    }

    public ProductDto() {

    }


    public void setImagePath(String imagePath) {
    }

    public void setImageUrl(String imageUrl) {
    }

    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
        try {
            this.imageData = imageFile.getBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
