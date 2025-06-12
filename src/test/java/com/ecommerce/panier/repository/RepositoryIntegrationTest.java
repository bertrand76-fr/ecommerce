package com.ecommerce.panier.repository;

import com.ecommerce.panier.model.client.TypeClient;
import com.ecommerce.panier.model.produit.TypeProduit;
import com.ecommerce.panier.model.tarification.CleTarification;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Tests d'intégration avec données MAIN - Auto-sync")
class RepositoryIntegrationTest {
    
    
    @Test
    @DisplayName("Cohérence des fichiers MAIN - lecture directe")
    void mainFiles_shouldBeCoherent() throws Exception {
        // Lecture directe des fichiers main (bypass Spring)
        ObjectMapper mapper = new ObjectMapper();
        
        // Lecture fichiers main
        Map<String, String> mainProduits = lireFichierMainDepuisFileSystem(mapper, "data/typeproduits.json");
        Map<String, String> mainClients = lireFichierMainDepuisFileSystem(mapper, "data/typeclients.json");
        List<TarificationDto> mainTarifications = lireTarificationsMainDepuisFileSystem(mapper, "data/tarifications.json");
        
        System.out.println("📁 Lecture directe des fichiers MAIN");
        System.out.println("🔍 Produits MAIN: " + mainProduits.keySet());
        System.out.println("🔍 Clients MAIN: " + mainClients.keySet());
        System.out.println("🔍 Règles MAIN: " + mainTarifications.size());
        
        // Vérification cohérence
        for (TarificationDto tarif : mainTarifications) {
            assertTrue(mainProduits.containsKey(tarif.typeProduit()), 
                      "❌ Produit manquant dans typeproduits.json: " + tarif.typeProduit());
            
            assertTrue(mainClients.containsKey(tarif.typeClient()),
                      "❌ Client manquant dans typeclients.json: " + tarif.typeClient());
        }
        
        // Vérification que toutes les combinaisons existent
        int combinaisonsAttendues = mainProduits.size() * mainClients.size();
        assertEquals(combinaisonsAttendues, mainTarifications.size(),
                    "❌ Nombre de règles incorrect. Attendu: " + combinaisonsAttendues + 
                    " (produits:" + mainProduits.size() + " x clients:" + mainClients.size() + ")");
        
        System.out.println("✅ Fichiers MAIN cohérents !");
    }
    
    
    private Map<String, String> lireFichierMainDepuisFileSystem(ObjectMapper mapper, String fichier) throws Exception {
        // Chemin depuis la racine du projet
        Path mainFile = Paths.get("src/main/resources/" + fichier);
        
        if (!Files.exists(mainFile)) {
            throw new RuntimeException("Fichier main non trouvé: " + mainFile);
        }
        
        try (InputStream input = Files.newInputStream(mainFile)) {
            return mapper.readValue(input, new TypeReference<Map<String, String>>() {});
        }
    }

    private List<TarificationDto> lireTarificationsMainDepuisFileSystem(ObjectMapper mapper, String fichier) throws Exception {
        // Chemin depuis la racine du projet
        Path mainFile = Paths.get("src/main/resources/" + fichier);
        
        if (!Files.exists(mainFile)) {
            throw new RuntimeException("Fichier main non trouvé: " + mainFile);
        }
        
        try (InputStream input = Files.newInputStream(mainFile)) {
            return mapper.readValue(input, new TypeReference<List<TarificationDto>>() {});
        }
    }
    

    
    // DTO local pour lecture
    public record TarificationDto(String typeProduit, String typeClient, double prix) {}
}
