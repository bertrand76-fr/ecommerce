package com.ecommerce.panier.repository;

import com.ecommerce.panier.model.client.TypeClient;
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
public class TypeClientRepository {

    private static final String TYPECLIENT_CONFIG = "data/typeclients.json";

    @Value("${app.data.typeclients:" + TYPECLIENT_CONFIG + "}")
    private String configFile;

    private Map<String, TypeClient> typeClients;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    public void init() {
        ensureInitialized();
    }

    private void ensureInitialized() {
        if (typeClients != null) {
            return; // Déjà initialisé
        }
        chargerTypeClients(TYPECLIENT_CONFIG);
    }

    private void chargerTypeClients(String fichier) {
        this.typeClients = new HashMap<>();

        try (InputStream input = getClass().getClassLoader().getResourceAsStream(fichier)) {
            if (input == null) {
                throw new RuntimeException("Fichier de configuration " + fichier + " non trouvé");
            }

            Map<String, String> data = objectMapper.readValue(input, new TypeReference<Map<String, String>>() {});

            for (Map.Entry<String, String> entry : data.entrySet()) {
                typeClients.put(entry.getKey(), new TypeClient(entry.getValue()));
            }

        } catch (IOException e) {
            throw new RuntimeException("Erreur lors du chargement de " + fichier, e);
        }
    }

    public Map<String, TypeClient> findAll() {
        ensureInitialized();
        return new HashMap<>(typeClients);
    }

    public Optional<TypeClient> findByCode(String code) {
        ensureInitialized();
        return Optional.ofNullable(typeClients.get(code));
    }
}