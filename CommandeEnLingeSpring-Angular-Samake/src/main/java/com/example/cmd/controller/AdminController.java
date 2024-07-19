package com.example.cmd.controller;
import com.example.cmd.model.*;
import com.example.cmd.repository.CategoryRepository;
import com.example.cmd.service.CategoryService;
import com.example.cmd.service.ProductService;
import com.example.cmd.service.ProduitVariantService;
import com.example.cmd.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private CategoryService categoryService;


    @Autowired
    private ProductService productService;

    @Autowired
    private ProduitVariantService productVariantService;


    // Endpoints pour les rôles

    @PostMapping("/roles")
    public ResponseEntity<String> ajouterRole(@RequestBody RoleType roleType) {
        String message = utilisateurService.ajouterRoleType(roleType);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @PutMapping("/roles/{id}")
    public ResponseEntity<String> modifierRole(@PathVariable Long id, @RequestBody RoleType roleTypeDetails) {
        String message = utilisateurService.modifierRoleType(id, roleTypeDetails);
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/roles/{id}")
    public ResponseEntity<String> supprimerRole(@PathVariable Long id) {
        String message = utilisateurService.supprimerRoleType(id);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/roles")
    public ResponseEntity<List<RoleType>> lireRoles() {
        List<RoleType> roles = utilisateurService.lireRoleTypes();
        return ResponseEntity.ok(roles);
    }

    @PostMapping("/ajouterClient")
    public ResponseEntity<String> ajouterClient(@RequestBody Client client, Authentication authentication) {
        // Obtenir le rôle de l'utilisateur connecté
        String currentUserRole = authentication.getAuthorities().iterator().next().getAuthority();

        String message = utilisateurService.ajouterClient(client, currentUserRole);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/creeradmin")
    public ResponseEntity<String> ajouterAdmin(@RequestBody Admin admin, Authentication authentication) {
        // Obtenir le rôle de l'utilisateur connecté
        String currentUserRole = authentication.getAuthorities().iterator().next().getAuthority();

        if (!currentUserRole.equals("ROLE_ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Vous n'avez pas les droits nécessaires pour cette opération.");
        }

        String message = utilisateurService.ajouterAdmin(admin);
        return ResponseEntity.ok(message);
    }

    @PutMapping("/modifieradmin/{id}")
    public ResponseEntity<String> modifierAdmin(@PathVariable Long id, @RequestBody Admin admin) {
        String message = utilisateurService.modifierAdmin(id, admin);
        return ResponseEntity.ok(message);
    }

    @PutMapping("/modifierMotDePasse")
    public ResponseEntity<String> modifierMotDePasse(@RequestBody Map<String, String> requestBody) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            String nouveauMotDePasse = requestBody.get("nouveauMotDePasse");
            if (nouveauMotDePasse == null || nouveauMotDePasse.isEmpty()) {
                return ResponseEntity.badRequest().body("Le nouveau mot de passe ne peut pas être vide.");
            }

            String message = utilisateurService.modifiermotDePasse(username, nouveauMotDePasse);
            return ResponseEntity.ok(message);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/creerpersonnel")
    public ResponseEntity<String> ajouterPersonnel(@RequestBody Personnel personnel) {
        String message = utilisateurService.ajouterPersonnel(personnel);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }

    @PutMapping("/modifierpersonnel/{id}")
    public ResponseEntity<String> modifierPersonnel(@PathVariable Long id, @RequestBody Personnel personnel) {
        String message = utilisateurService.modifierPersonnel(id, personnel);
        return ResponseEntity.ok(message);
    }


    @GetMapping("/listutilisateurs")
    public ResponseEntity<List<Utilisateur>> lireUtilisateurs() {
        List<Utilisateur> utilisateurs = utilisateurService.lireUtilisateurs();
        return ResponseEntity.ok(utilisateurs);
    }

    @DeleteMapping("/supprimerutilisateur/{id}")
    public ResponseEntity<String> supprimerUtilisateur(@PathVariable Long id) {
        String message = utilisateurService.supprimerUtilisateur(id);
        return ResponseEntity.ok(message);
    }

    // Catégorie

    @PostMapping("/categories")
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        Category createdCategory = categoryService.createCategory(category.getName());
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }


    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        return new ResponseEntity<>(categoryService.getAllCategories(), HttpStatus.OK);
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable Long id) {
        Category category = categoryService.getCategory(id);
        if (category != null) {
            return new ResponseEntity<>(category, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        Category updatedCategory = categoryService.updateCategory(id, category.getName());
        if (updatedCategory != null) {
            return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>("Catégorie supprimée avec succès!", HttpStatus.OK);
    }

    @PostMapping("/produits")
    public ResponseEntity<String> ajouterProduit(@RequestParam("name") String name,
                                                 @RequestParam("description") String description,
                                                 @RequestParam("prix") BigDecimal prix,
                                                 @RequestParam("quantite") int quantite,
                                                 @RequestPart(value = "imageFile", required = false) MultipartFile imageFile,
                                                 @RequestParam("categoryId") Long categoryId) {
        // Créer un DTO de produit
        ProductDto productDto = new ProductDto();
        productDto.setName(name);
        productDto.setDescription(description);
        productDto.setPrix(prix);
        productDto.setQuantite(quantite);
        productDto.setImageFile(imageFile);
        productDto.setCategoryId(categoryId);

        // Récupérer l'authentification actuelle
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Récupérer le nom d'utilisateur de l'utilisateur authentifié
        String username = authentication.getName();

        // Appeler le service pour créer le produit
        String message = productService.createProduct(productDto);

        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }


    @GetMapping("/produits")
    public ResponseEntity<List<ProductDto>> listeProduits() {
        List<ProductDto> produits = productService.getAllProducts();
        return ResponseEntity.ok(produits);
    }

    @DeleteMapping("/produits/{id}")
    public ResponseEntity<String> supprimerProduit(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Produit supprimé avec succès!");
    }

    @PutMapping("/produits/{id}")
    public ResponseEntity<String> modifierProduit(@PathVariable Long id,
                                                  @RequestParam("name") String name,
                                                  @RequestParam("description") String description,
                                                  @RequestParam("prix") BigDecimal prix,
                                                  @RequestParam("quantite") int quantite,
                                                  @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                                                  @RequestParam("categoryId") Long categoryId) {
        ProductDto productDto = new ProductDto();
        productDto.setName(name);
        productDto.setDescription(description);
        productDto.setPrix(prix);
        productDto.setQuantite(quantite);
        productDto.setCategoryId(categoryId);
        productDto.setImageFile(imageFile);

        String message = productService.updateProduct(id, productDto);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/{categoryId}/variants")
    public ProduitVariant createProductVariant(@PathVariable Long categoryId, @RequestParam String name) {
        return productVariantService.createProductVariant(name, categoryId);
    }

    @GetMapping("/{categoryId}/variants")
    public List<ProduitVariant> getVariantsByCategoryId(@PathVariable Long categoryId) {
        return productVariantService.getVariantsByCategoryId(categoryId);
    }

    @GetMapping("/{productId}/image")
    public ResponseEntity<byte[]> getProductImage(@PathVariable Long productId) {
        byte[] imageData = productService.getProductImage(productId);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "image/jpeg"); // ou le type MIME approprié pour votre image
        return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
    }
}
