package com.example.cmd.service;
import com.example.cmd.model.Category;
import com.example.cmd.model.ProductDto;
import com.example.cmd.model.Utilisateur;
import com.example.cmd.repository.CategoryRepository;
import com.example.cmd.repository.ProductRepository;
import com.example.cmd.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


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
        return productRepository.findAll();
    }

    public String createProduct(ProductDto productDto) {
        ProductDto product = new ProductDto();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrix(productDto.getPrix());
        product.setImageFile(productDto.getImageFile());
        product.setQuantite(productDto.getQuantite());

        Category category = categoryRepository.findById(productDto.getCategoryId()).orElse(null);
        product.setCategory(category);

        Utilisateur utilisateur = utilisateurRepository.findById(productDto.getUtilisateurId()).orElse(null);
        product.setUtilisateur(utilisateur);

        productRepository.save(product);
        return "Produit créé avec succès!";
    }

    public String updateProduct(Long id, ProductDto productDto) {
        var optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            ProductDto product = optionalProduct.get();
            product.setName(productDto.getName());
            product.setDescription(productDto.getDescription());
            product.setPrix(productDto.getPrix());
            product.setQuantite(productDto.getQuantite());
            product.setImageFile(productDto.getImageFile());

            Category category = categoryRepository.findById(productDto.getCategoryId()).orElse(null);
            product.setCategory(category);

            Utilisateur utilisateur = utilisateurRepository.findById(productDto.getUtilisateurId()).orElse(null);
            product.setUtilisateur(utilisateur);

            productRepository.save(product);
            return "Produit modifié avec succès!";
        } else {
            return "Produit non trouvé avec l'id : " + id;
        }
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

}
