package com.ecommerce.panier.repository;

import com.ecommerce.panier.model.client.TypeClient;
import com.ecommerce.panier.model.produit.TypeProduit;
import com.ecommerce.panier.model.tarification.CleTarification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Tests du TarificationRepository")
class TarificationRepositoryTest {
    
    @Autowired
    private TarificationRepository repository;
    
    @Test
    @DisplayName("Doit charger les règles de tarification")
    void findAll_shouldLoadTarifications() {
        // When
        Map<CleTarification, Double> tarifications = repository.findAll();
        
        // Then
        assertNotNull(tarifications);
        assertTrue(tarifications.size() > 0);
    }
    
    @Test
    @DisplayName("Doit retourner un prix pour une clé valide")
    void findPrix_shouldReturnPrice() {
        // Given
        TypeProduit produit = new TypeProduit("Téléphone haut de gamme");
        TypeClient client = new TypeClient("Particulier");
        CleTarification cle = new CleTarification(produit, client);
        
        // When
        Optional<Double> prix = repository.findPrix(cle);
        
        // Then
        assertTrue(prix.isPresent());
        assertEquals(1500.0, prix.get());
    }
}