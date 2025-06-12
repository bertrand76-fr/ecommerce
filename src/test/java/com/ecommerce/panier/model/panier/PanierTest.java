package com.ecommerce.panier.model.panier;

import com.ecommerce.panier.model.produit.TypeProduit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests de la classe Panier")
public class PanierTest {
    
    private Panier panier;
    private TypeProduit telephoneHG;
    private TypeProduit telephoneMG;
    private TypeProduit laptop;
    
    @BeforeEach
    void setUp() {
        panier = new Panier();
        telephoneHG = new TypeProduit("Téléphone haut de gamme");
        telephoneMG = new TypeProduit("Téléphone moyen de gamme");
        laptop = new TypeProduit("Ordinateur portable");
    }
    
    @Test
    @DisplayName("Un panier vide doit être créé")
    void nouveauPanier_shouldBeEmpty() {
        // Given/When - constructeur appelé dans setUp
        
        // Then
        assertTrue(panier.estVide());
        assertEquals(0, panier.getTypeProduits().size());
    }
    
    @Test
    @DisplayName("Ajouter un type de produit doit l'insérer dans le panier")
    void ajouterTypeProduit_shouldAddProduct() {
        // Given/When
        panier.ajouterTypeProduitQuantite(telephoneHG, 2);
        
        // Then
        assertFalse(panier.estVide());
        assertEquals(1, panier.getTypeProduits().size());
        assertEquals(2, panier.getTypeProduits().get(telephoneHG));
    }
    
    @Test
    @DisplayName("Ajouter plusieurs fois le même type doit additionner les quantités")
    void ajouterTypeProduit_shouldAddQuantities_whenSameProduct() {
        // Given/When
        panier.ajouterTypeProduitQuantite(telephoneHG, 2);
        panier.ajouterTypeProduitQuantite(telephoneHG, 3);
        
        // Then
        assertEquals(1, panier.getTypeProduits().size());
        assertEquals(5, panier.getTypeProduits().get(telephoneHG));
    }
    
    @Test
    @DisplayName("Ajouter différents types de produits")
    void ajouterTypeProduit_shouldAddMultipleProducts() {
        // Given/When
        panier.ajouterTypeProduitQuantite(telephoneHG, 2);
        panier.ajouterTypeProduitQuantite(laptop, 1);
        panier.ajouterTypeProduitQuantite(telephoneMG, 3);
        
        // Then
        assertEquals(3, panier.getTypeProduits().size());
        assertEquals(2, panier.getTypeProduits().get(telephoneHG));
        assertEquals(1, panier.getTypeProduits().get(laptop));
        assertEquals(3, panier.getTypeProduits().get(telephoneMG));
    }
    
    @Test
    @DisplayName("Ajouter une quantité négative ou nulle doit lever une exception")
    void ajouterTypeProduit_shouldThrowException_whenInvalidQuantity() {
        // Given/When/Then
        assertThrows(IllegalArgumentException.class, 
                    () -> panier.ajouterTypeProduitQuantite(telephoneHG, 0));
        
        assertThrows(IllegalArgumentException.class, 
                    () -> panier.ajouterTypeProduitQuantite(telephoneHG, -1));
    }
    
    @Test
    @DisplayName("Supprimer un type de produit doit le retirer du panier")
    void supprimerTypeProduit_shouldRemoveProduct() {
        // Given
        panier.ajouterTypeProduitQuantite(telephoneHG, 2);
        panier.ajouterTypeProduitQuantite(laptop, 1);
        
        // When
        panier.supprimerTypeProduit(telephoneHG);
        
        // Then
        assertEquals(1, panier.getTypeProduits().size());
        assertNull(panier.getTypeProduits().get(telephoneHG));
        assertEquals(1, panier.getTypeProduits().get(laptop));
    }
    
    @Test
    @DisplayName("Supprimer un type de produit inexistant ne doit pas lever d'exception")
    void supprimerTypeProduit_shouldNotThrow_whenProductNotExists() {
        // Given - panier vide
        
        // When/Then
        assertDoesNotThrow(() -> panier.supprimerTypeProduit(telephoneHG));
    }
    
    @Test
    @DisplayName("Vider le panier doit supprimer tous les produits")
    void vider_shouldClearAllProducts() {
        // Given
        panier.ajouterTypeProduitQuantite(telephoneHG, 2);
        panier.ajouterTypeProduitQuantite(laptop, 1);
        
        // When
        panier.vider();
        
        // Then
        assertTrue(panier.estVide());
        assertEquals(0, panier.getTypeProduits().size());
    }
    
    @Test
    @DisplayName("getTypeProduits doit etre vide apres clear")
    void getTypeProduits_shouldReturnEmptyProducts() {
        // Given
        panier.ajouterTypeProduitQuantite(telephoneHG, 2);
        
        // When
        Map<TypeProduit, Integer> produits = panier.getTypeProduits();
        produits.clear(); // Tentative de modification
        
        // Then
        assertEquals(0, panier.getTypeProduits().size());
    }
}