package com.ecommerce.panier.repository;

import com.ecommerce.panier.model.client.TypeClient;
import com.ecommerce.panier.model.produit.TypeProduit;
import com.ecommerce.panier.model.tarification.CleTarification;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class TarificationRepository {

    private static final String TARIFICATION_CONFIG = "data/tarifications.json";

    @Autowired
    private TypeProduitRepository typeProduitRepository;
    
    @Autowired
    private TypeClientRepository typeClientRepository;

    private Map<CleTarification, Double> tarifications;
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    // Record DTO pour Jackson
    public record TarificationDto(String typeProduit, String typeClient, double prix) {}

    @PostConstruct
    public void init() {
        ensureInitialized();
    }

    private void ensureInitialized() {
        if (tarifications != null) {
            return;
        }
        chargerTarifications(TARIFICATION_CONFIG);
    }

    private void chargerTarifications(String fichier) {
        this.tarifications = new HashMap<>();

        try (InputStream input = getClass().getClassLoader().getResourceAsStream(fichier)) {
            if (input == null) {
                throw new RuntimeException("Fichier de configuration " + fichier + " non trouvé");
            }

            List<TarificationDto> data = objectMapper.readValue(input, new TypeReference<List<TarificationDto>>() {});

            for (TarificationDto dto : data) {
                TypeProduit typeProduit = typeProduitRepository.findByName(dto.typeProduit).orElse(null);
                TypeClient typeClient = typeClientRepository.findByName(dto.typeClient).orElse(null);
                
                if (typeProduit != null && typeClient != null) {
                    CleTarification cle = new CleTarification(typeProduit, typeClient);
                    tarifications.put(cle, dto.prix);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Erreur lors du chargement de " + fichier, e);
        }
    }

    public Map<CleTarification, Double> findAll() {
        ensureInitialized();
        return new HashMap<>(tarifications);
    }

    public Optional<Double> findPrix(CleTarification cle) {
        ensureInitialized();
        return Optional.ofNullable(tarifications.get(cle));
    }


}