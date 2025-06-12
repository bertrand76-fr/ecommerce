package com.ecommerce.panier.service;

import com.ecommerce.panier.model.client.Client;
import com.ecommerce.panier.model.client.ClientParticulier;
import com.ecommerce.panier.model.client.ClientProfessionnel;
import com.ecommerce.panier.model.client.TypeClient;
import com.ecommerce.panier.repository.TypeClientRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Tests du ClientTypeService")
class TypeClientServiceTest {
    
    @Autowired
    private TypeClientService typeClientService;
    
    @Autowired
    private TypeClientRepository typeClientRepository;
    
    private static TypeClient particulier;
    private static TypeClient professionnelPetit;
    private static TypeClient professionnelGros;
    
    @BeforeAll
    void setUpClass() {
        particulier = typeClientRepository.findByCode("PARTICULIER").orElseThrow();
        professionnelPetit = typeClientRepository.findByCode("PROFESSIONNEL_PETIT").orElseThrow();
        professionnelGros = typeClientRepository.findByCode("PROFESSIONNEL_GROS").orElseThrow();
    }
    
    @Test
    @DisplayName("Un client particulier doit retourner le type PARTICULIER")
    void determineTypeClient_shouldReturnParticulier_forClientParticulier() {
        // Given
        Client client = new ClientParticulier("C001", "Dupont", "Jean");
        
        // When
        TypeClient typeClient = typeClientService.determineTypeClient(client);
        
        // Then
        assertEquals(particulier, typeClient);
        assertEquals("Particulier", typeClient.getNom());
    }
    
    @Test
    @DisplayName("Un client professionnel avec CA < 10M€ doit retourner PROFESSIONNEL_PETIT")
    void determineTypeClient_shouldReturnProfessionnelPetit_forSmallBusiness() {
        // Given
        Client client = new ClientProfessionnel("C002", "Petite SARL", "123456789", 5_000_000);
        
        // When
        TypeClient typeClient = typeClientService.determineTypeClient(client);
        
        // Then
        assertEquals(professionnelPetit, typeClient);
        assertEquals("Professionnel < 10M€", typeClient.getNom());
    }
    
    @Test
    @DisplayName("Un client professionnel avec CA > 10M€ doit retourner PROFESSIONNEL_GROS")
    void determineTypeClient_shouldReturnProfessionnelGros_forBigBusiness() {
        // Given
        Client client = new ClientProfessionnel("C003", "Grosse SA", "987654321", 50_000_000);
        
        // When
        TypeClient typeClient = typeClientService.determineTypeClient(client);
        
        // Then
        assertEquals(professionnelGros, typeClient);
        assertEquals("Professionnel > 10M€", typeClient.getNom());
    }
}