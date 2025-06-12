package com.ecommerce.panier.repository;

import com.ecommerce.panier.model.client.TypeClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Tests du TypeClientRepository")
class TypeClientRepositoryTest {
    
    @Autowired
    private TypeClientRepository repository;
    
    @Test
    @DisplayName("Doit charger tous les types de clients depuis le fichier JSON")
    void findAll_shouldLoadAllTypeClients() {
        // When
        Map<String, TypeClient> typeClients = repository.findAll();
        
        // Then
        assertNotNull(typeClients);
        assertEquals(3, typeClients.size());
        assertTrue(typeClients.containsKey("PARTICULIER"));
        assertTrue(typeClients.containsKey("PROFESSIONNEL_PETIT"));
        assertTrue(typeClients.containsKey("PROFESSIONNEL_GROS"));
    }
    
    @Test
    @DisplayName("Doit retourner un TypeClient par son nom")
    void findByName_shouldReturnTypeClient_whenExists() {
        // When
        Optional<TypeClient> typeClient = repository.findByName("PARTICULIER");
        
        // Then
        assertTrue(typeClient.isPresent());
        assertEquals("Particulier", typeClient.get().getNom());
    }
    
    @Test
    @DisplayName("Doit retourner Optional.empty() pour un nom inexistant")
    void findByName_shouldReturnEmpty_whenNotExists() {
        // When
        Optional<TypeClient> typeClient = repository.findByName("INEXISTANT");
        
        // Then
        assertFalse(typeClient.isPresent());
    }
    
    @Test
    @DisplayName("Doit retourner la bonne valeur pour chaque type de client")
    void findByName_shouldReturnCorrectValues() {
        // When/Then
        Optional<TypeClient> particulier = repository.findByName("PARTICULIER");
        assertTrue(particulier.isPresent());
        assertEquals("Particulier", particulier.get().getNom());
        
        Optional<TypeClient> profPetit = repository.findByName("PROFESSIONNEL_PETIT");
        assertTrue(profPetit.isPresent());
        assertEquals("Professionnel < 10M€", profPetit.get().getNom());
        
        Optional<TypeClient> profGros = repository.findByName("PROFESSIONNEL_GROS");
        assertTrue(profGros.isPresent());
        assertEquals("Professionnel > 10M€", profGros.get().getNom());
    }
}