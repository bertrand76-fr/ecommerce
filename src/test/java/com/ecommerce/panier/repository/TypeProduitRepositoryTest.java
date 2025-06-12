package com.ecommerce.panier.repository;

import com.ecommerce.panier.model.produit.TypeProduit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Tests du TypeProduitRepository")
class TypeProduitRepositoryTest {
    
	@Autowired
    private TypeProduitRepository repository;
    
    @BeforeEach
    void setUp() {
        repository = new TypeProduitRepository();
    }
    
    @Test
    @DisplayName("Doit charger tous les types de produits depuis le fichier JSON")
    void findAll_shouldLoadAllTypeProduits() {
        // When
        Map<String, TypeProduit> typeProduits = repository.findAll();
        
        // Then
        assertNotNull(typeProduits);
        assertEquals(3, typeProduits.size());
        assertTrue(typeProduits.containsKey("TELEPHONE_HAUT_GAMME"));
        assertTrue(typeProduits.containsKey("TELEPHONE_MOYEN_GAMME"));
        assertTrue(typeProduits.containsKey("LAPTOP"));
    }
    
    @Test
    @DisplayName("Doit retourner un TypeProduit par son nom")
    void findByName_shouldReturnTypeProduit_whenExists() {
        // When
        Optional<TypeProduit> typeProduit = repository.findByCode("TELEPHONE_HAUT_GAMME");
        
        // Then
        assertTrue(typeProduit.isPresent());
        assertEquals("Téléphone haut de gamme", typeProduit.get().getNom());
    }
    
    @Test
    @DisplayName("Doit retourner Optional.empty() pour un nom inexistant")
    void findByName_shouldReturnEmpty_whenNotExists() {
        // When
        Optional<TypeProduit> typeProduit = repository.findByCode("INEXISTANT");
        
        // Then
        assertFalse(typeProduit.isPresent());
    }
    
    @Test
    @DisplayName("Doit retourner la bonne valeur pour chaque type de produit")
    void findByName_shouldReturnCorrectValues() {
        // When/Then
        Optional<TypeProduit> telephoneHG = repository.findByCode("TELEPHONE_HAUT_GAMME");
        assertTrue(telephoneHG.isPresent());
        assertEquals("Téléphone haut de gamme", telephoneHG.get().getNom());
        
        Optional<TypeProduit> telephoneMG = repository.findByCode("TELEPHONE_MOYEN_GAMME");
        assertTrue(telephoneMG.isPresent());
        assertEquals("Téléphone moyen de gamme", telephoneMG.get().getNom());
        
        Optional<TypeProduit> laptop = repository.findByCode("LAPTOP");
        assertTrue(laptop.isPresent());
        assertEquals("Ordinateur portable", laptop.get().getNom());
    }
    
    @Test
    @DisplayName("Ne doit pas retourner null pour findAll")
    void findAll_shouldNeverReturnNull() {
        // When
        Map<String, TypeProduit> result = repository.findAll();
        
        // Then
        assertNotNull(result);
    }
        
}