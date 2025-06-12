package com.ecommerce.panier.service;

import com.ecommerce.panier.model.client.Client;
import com.ecommerce.panier.model.client.ClientParticulier;
import com.ecommerce.panier.model.client.ClientProfessionnel;
import com.ecommerce.panier.model.produit.TypeProduit;
import com.ecommerce.panier.repository.TypeProduitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@DisplayName("Tests du PricingService")
class PricingServiceTest {

    @Autowired
    private PricingService pricingService;
    
    @Autowired
    private TypeProduitRepository typeProduitRepository;
    
    private TypeProduit laptop;
    private Client clientParticulier;
    private Client clientProfessionnelPetit;
    private Client clientProfessionnelGros;
    
    @BeforeEach
    void setUp() {
        // TypeProduit à tester - récupéré via repository
        laptop = typeProduitRepository.findByCode("LAPTOP")
                .orElseThrow(() -> new RuntimeException("TypeProduit LAPTOP non trouvé"));
        
        // Création des 3 types de clients
        clientParticulier = new ClientParticulier("C001", "Dupont", "Jean");
        clientProfessionnelPetit = new ClientProfessionnel("C002", "Petite SARL", "123456789", 5_000_000); // CA < 10M€
        clientProfessionnelGros = new ClientProfessionnel("C003", "Grosse SA", "987654321", 50_000_000); // CA > 10M€
    }
    
    @Test
    @DisplayName("Prix LAPTOP pour client PARTICULIER doit être 1200€")
    void getPrix_shouldReturn1200_forLaptopAndParticulier() {
        // When
        double prix = pricingService.getPrix(laptop, clientParticulier);
        
        // Then
        assertEquals(1200.0, prix, 0.01);
    }
    
    @Test
    @DisplayName("Prix LAPTOP pour client PROFESSIONNEL_PETIT doit être 1000€")
    void getPrix_shouldReturn1000_forLaptopAndProfessionnelPetit() {
        // When
        double prix = pricingService.getPrix(laptop, clientProfessionnelPetit);
        
        // Then
        assertEquals(1000.0, prix, 0.01);
    }
    
    @Test
    @DisplayName("Prix LAPTOP pour client PROFESSIONNEL_GROS doit être 900€")
    void getPrix_shouldReturn900_forLaptopAndProfessionnelGros() {
        // When
        double prix = pricingService.getPrix(laptop, clientProfessionnelGros);
        
        // Then
        assertEquals(900.0, prix, 0.01);
    }
    
    @Test
    @DisplayName("Exception si TypeProduit est null")
    void getPrix_shouldThrowException_whenTypeProduitIsNull() {
        // When & Then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> pricingService.getPrix(null, clientParticulier)
        );
        
        assertEquals("Le type de produit ne peut pas être null", exception.getMessage());
    }
    
    @Test
    @DisplayName("Exception si Client est null")
    void getPrix_shouldThrowException_whenClientIsNull() {
        // When & Then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> pricingService.getPrix(laptop, null)
        );
        
        assertEquals("Le client ne peut pas être null", exception.getMessage());
    }
}