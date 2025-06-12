package com.ecommerce.panier.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;


import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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
	    
	    // Vérification cohérence avec détail des clés manquantes
	    for (TarificationDto tarif : mainTarifications) {
	        assertTrue(mainProduits.containsKey(tarif.typeProduit()), 
	                  "❌ Produit manquant dans typeproduits.json: " + tarif.typeProduit());
	        
	        assertTrue(mainClients.containsKey(tarif.typeClient()),
	                  "❌ Client manquant dans typeclients.json: " + tarif.typeClient());
	    }
	    
	    // Analyse des clés manquantes
	    System.out.println("\n🔍 Analyse des clés de tarification:");
	    
	    List<String> clesManquantes = new ArrayList<>();
	    for (String produit : mainProduits.keySet()) {
	        for (String client : mainClients.keySet()) {
	            String cleAttendue = produit + " + " + client;
	            
	            boolean cleTrouvee = mainTarifications.stream()
	                .anyMatch(tarif -> tarif.typeProduit().equals(produit) && 
	                                 tarif.typeClient().equals(client));
	            
	            if (!cleTrouvee) {
	                clesManquantes.add(cleAttendue);
	            }
	        }
	    }
	    
	    // Vérification finale
	    
	    int combinaisonsAttendues = mainProduits.size() * mainClients.size();
	    if (!clesManquantes.isEmpty()) {
		    System.out.println("❌ Fichiers MAIN incohérent - Toutes les " + combinaisonsAttendues + " clés attendues ne sont pas présentes !");
            System.out.println("❌ Clé manquantes: " + clesManquantes);
	        fail("❌ Clés de tarification manquantes: " + clesManquantes);
	    }
	    	    
	    System.out.println("✅ Fichiers MAIN cohérents - Toutes les " + combinaisonsAttendues + " clés attenduees sont présentes !");
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
