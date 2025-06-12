package com.ecommerce.panier.service;

import com.ecommerce.panier.model.client.Client;
import com.ecommerce.panier.model.client.ClientParticulier;
import com.ecommerce.panier.model.client.ClientProfessionnel;
import com.ecommerce.panier.model.panier.Panier;
import com.ecommerce.panier.model.produit.TypeProduit;
import com.ecommerce.panier.repository.TypeProduitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Tests du PanierService")
class PanierServiceTest {

    @Autowired
    private PanierService panierService;
    
    @Autowired
    private TypeProduitRepository typeProduitRepository;
    
    private Panier panier;
    private TypeProduit telephoneHautGamme;
    private TypeProduit telephoneMoyenGamme;
    private TypeProduit laptop;
    
    private Client clientParticulier;
    private Client clientProfessionnelPetit;
    private Client clientProfessionnelGros;
    
    @BeforeEach
    void setUp() {
        // Récupération des types de produits via repository
        telephoneHautGamme = typeProduitRepository.findByCode("TELEPHONE_HAUT_GAMME")
                .orElseThrow(() -> new RuntimeException("TypeProduit TELEPHONE_HAUT_GAMME non trouvé"));
        telephoneMoyenGamme = typeProduitRepository.findByCode("TELEPHONE_MOYEN_GAMME")
                .orElseThrow(() -> new RuntimeException("TypeProduit TELEPHONE_MOYEN_GAMME non trouvé"));
        laptop = typeProduitRepository.findByCode("LAPTOP")
                .orElseThrow(() -> new RuntimeException("TypeProduit LAPTOP non trouvé"));
        
        // Création du panier avec les 3 types de produits (1 de chaque)
        panier = new Panier();
        panier.ajouterTypeProduitQuantite(telephoneHautGamme, 1);
        panier.ajouterTypeProduitQuantite(telephoneMoyenGamme, 1);
        panier.ajouterTypeProduitQuantite(laptop, 1);
        
        // Création des 3 types de clients
        clientParticulier = new ClientParticulier("C001", "Dupont", "Jean");
        clientProfessionnelPetit = new ClientProfessionnel("C002", "Petite SARL", "123456789", 5_000_000); // CA < 10M€
        clientProfessionnelGros = new ClientProfessionnel("C003", "Grosse SA", "987654321", 50_000_000); // CA > 10M€
    }
    
    @Test
    @DisplayName("Coût total panier pour client PARTICULIER")
    void calculerCoutTotal_shouldReturnCorrectTotal_forParticulier() {
        // Given - panier avec 1 téléphone haut gamme + 1 téléphone moyen gamme + 1 laptop
        // Expected: 1500.0 + 800.0 + 1200.0 = 3500.0€
        
        // When
        double total = panierService.calculerCoutTotal(panier, clientParticulier);
        
        // Then
        assertEquals(3500.0, total, 0.01);
    }
    
    @Test
    @DisplayName("Coût total panier pour client PROFESSIONNEL_PETIT")
    void calculerCoutTotal_shouldReturnCorrectTotal_forProfessionnelPetit() {
        // Given - panier avec 1 téléphone haut gamme + 1 téléphone moyen gamme + 1 laptop
        // Expected: 1150.0 + 600.0 + 1000.0 = 2750.0€
        
        // When
        double total = panierService.calculerCoutTotal(panier, clientProfessionnelPetit);
        
        // Then
        assertEquals(2750.0, total, 0.01);
    }
    
    @Test
    @DisplayName("Coût total panier pour client PROFESSIONNEL_GROS")
    void calculerCoutTotal_shouldReturnCorrectTotal_forProfessionnelGros() {
        // Given - panier avec 1 téléphone haut gamme + 1 téléphone moyen gamme + 1 laptop
        // Expected: 1000.0 + 550.0 + 900.0 = 2450.0€
        
        // When
        double total = panierService.calculerCoutTotal(panier, clientProfessionnelGros);
        
        // Then
        assertEquals(2450.0, total, 0.01);
    }
    
    @Test
    @DisplayName("Coût total panier vide doit être 0")
    void calculerCoutTotal_shouldReturn0_forEmptyPanier() {
        // Given
        Panier panierVide = new Panier();
        
        // When
        double total = panierService.calculerCoutTotal(panierVide, clientParticulier);
        
        // Then
        assertEquals(0.0, total, 0.01);
    }
    
    @Test
    @DisplayName("Exception si panier est null")
    void calculerCoutTotal_shouldThrowException_whenPanierIsNull() {
        // When & Then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> panierService.calculerCoutTotal(null, clientParticulier)
        );
        
        assertEquals("Le panier ne peut pas être null", exception.getMessage());
    }
    
    @Test
    @DisplayName("Exception si client est null")
    void calculerCoutTotal_shouldThrowException_whenClientIsNull() {
        // When & Then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> panierService.calculerCoutTotal(panier, null)
        );
        
        assertEquals("Le client ne peut pas être null", exception.getMessage());
    }
}