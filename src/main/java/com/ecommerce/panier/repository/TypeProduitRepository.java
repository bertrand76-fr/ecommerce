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
    
    private static final String TYPEPRODUIT_CONFIG = "data/typeproduits.json";
    
    @Value("${app.data.typeproduits:" + TYPEPRODUIT_CONFIG + "}")
    private String configFile;
    
    private Map<String, TypeProduit> typeProduits;
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @PostConstruct
    public void init() {
        ensureInitialized();
    }
    
    private void ensureInitialized() {
        if (typeProduits != null) {
            return; // Déjà initialisé
        }
        chargerTypeProduits(TYPEPRODUIT_CONFIG);
    }
    
    private void chargerTypeProduits(String fichier) {
        this.typeProduits = new HashMap<>();
        
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(fichier)) {
            if (input == null) {
                throw new RuntimeException("Fichier de configuration " + fichier + " non trouvé");
            }
            
            Map<String, String> data = objectMapper.readValue(input, new TypeReference<Map<String, String>>() {});
            
            for (Map.Entry<String, String> entry : data.entrySet()) {
                typeProduits.put(entry.getKey(), new TypeProduit(entry.getValue()));
            }
            
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors du chargement de " + fichier, e);
        }
    }
    
    public Map<String, TypeProduit> findAll() {
        ensureInitialized();
        return new HashMap<>(typeProduits);
    }
    
    public Optional<TypeProduit> findByCode(String code) {
        ensureInitialized();
        return Optional.ofNullable(typeProduits.get(code));
    }
}