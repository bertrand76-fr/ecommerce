package com.ecommerce.panier.repository;

import com.ecommerce.panier.model.produit.TypeProduit;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class TypeProduitRepository {
    
    @Value("${app.data.typeproduits:data/typeproduits.json}")
    private String configFile;
    
    private Map<String, TypeProduit> typeProduits;
    private final ObjectMapper objectMapper = new ObjectMapper();

    
    @PostConstruct
    public void init() {
        System.out.println("🚀 PostConstruct - Instance: " + this.hashCode());
        if (typeProduits != null) {
            System.out.println("⚠️ Init appelé mais typeProduits déjà initialisé");
            return;
        }
        System.out.println("🚀 PostConstruct appelé !");
        System.out.println("📁 Config file: " + configFile);
        chargerTypeProduits();
        System.out.println("📦 Chargé: " + typeProduits.size() + " produits");
        System.out.println("📦 Chargé: " + typeProduits ); 
    }    
    
    private void chargerTypeProduits() {
        this.typeProduits = new HashMap<>();
        
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(configFile)) {
            if (input == null) {
                throw new RuntimeException("Fichier de configuration " + configFile + " non trouvé");
            }
            
            Map<String, String> data = objectMapper.readValue(input, new TypeReference<Map<String, String>>() {});
            
            for (Map.Entry<String, String> entry : data.entrySet()) {
                typeProduits.put(entry.getKey(), new TypeProduit(entry.getValue()));
            }
            
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors du chargement de " + configFile, e);
        }
    }
   
    public Map<String, TypeProduit> findAll() {
    	 System.out.println("🔍 findAll() - Instance: " + this.hashCode());
        if (typeProduits == null) {
            System.out.println("⚠️ typeProduits null dans findAll() - réinitialisation");
            init(); // Force la réinitialisation
        }
        return new HashMap<>(typeProduits);
    }
    
//    public Map<String, TypeProduit> findAll() {
//        return new HashMap<>(typeProduits);
//    }
    
    public Optional<TypeProduit> findByName(String nom) {
        return Optional.ofNullable(typeProduits.get(nom));
    }
}