package com.example.cmd.service;

import com.example.cmd.model.Category;
import com.example.cmd.model.ProductDto;
import com.example.cmd.model.Utilisateur;
import com.example.cmd.repository.CategoryRepository;
import com.example.cmd.repository.ProductRepository;
import com.example.cmd.repository.UtilisateurRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final UtilisateurRepository utilisateurRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository,
                          UtilisateurRepository utilisateurRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    public Optional<ProductDto> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream().map(product -> {
            if (product.getImageData() != null) {
                String base64Image = Base64.getEncoder().encodeToString(product.getImageData());
                product.setImagePath("data:image/jpeg;base64," + base64Image);
            }
            return product;
        }).collect(Collectors.toList());
    }

    public String createProduct(ProductDto productDto) {
        // Récupérer l'authentification actuelle
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Récupérer le nom d'utilisateur de l'utilisateur authentifié
        String username = authentication.getName();

        // Trouver l'utilisateur par nom d'utilisateur
        Utilisateur utilisateur = utilisateurRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé avec le nom d'utilisateur : " + username));

        // Créer un nouveau produit et définir ses propriétés
        ProductDto product = new ProductDto();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrix(productDto.getPrix());
        product.setQuantite(productDto.getQuantite());
        product.setCategory(categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Catégorie non trouvée avec l'ID : " + productDto.getCategoryId())));
        product.setUtilisateur(utilisateur);

        // Gérer le fichier image (s'il est fourni)
        if (productDto.getImageFile() != null && !productDto.getImageFile().isEmpty()) {
            try {
                product.setImageFile(productDto.getImageFile());
            } catch (Exception e) {
                throw new RuntimeException("Error saving image file", e);
            }
        }


        // Enregistrer le produit
        productRepository.save(product);

        return "Produit créé avec succès!";
    }

    // Méthode pour enregistrer le fichier image
    private String saveImageFile(MultipartFile imageFile) {
        // Enregistrez le fichier image et retournez le chemin
        String imageName = FilenameUtils.getBaseName(imageFile.getOriginalFilename()) + "." + FilenameUtils.getExtension(imageFile.getOriginalFilename());
        // Logique pour enregistrer le fichier (ex. sur le disque, dans le stockage cloud, etc.)
        return imageName; // Retournez le chemin enregistré
    }


    public String updateProduct(Long id, ProductDto productDto) {
        var optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            ProductDto existingProduct = optionalProduct.get();
            existingProduct.setName(productDto.getName());
            existingProduct.setDescription(productDto.getDescription());
            existingProduct.setPrix(productDto.getPrix());
            existingProduct.setQuantite(productDto.getQuantite());

            Category category = categoryRepository.findById(productDto.getCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Catégorie non trouvée"));
            existingProduct.setCategory(category);

            Utilisateur utilisateur = utilisateurRepository.findById(productDto.getUtilisateurId())
                    .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé"));
            existingProduct.setUtilisateur(utilisateur);

            // Handle image file
            if (productDto.getImageFile() != null && !productDto.getImageFile().isEmpty()) {
                String imageUrl = saveImageFile(productDto.getImageFile());
                existingProduct.setImageUrl(imageUrl);
            }

            productRepository.save(existingProduct);
            return "Produit modifié avec succès!";
        } else {
            return "Produit non trouvé avec l'id : " + id;
        }
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public byte[] getProductImage(Long productId) {
        Optional<ProductDto> product = productRepository.findById(productId);
        if (product.isPresent() && product.get().getImageData() != null) {
            return product.get().getImageData();
        } else {
            throw new RuntimeException("Product image not found for id: " + productId);
        }
    }

}
