package com.ecommerce.panier.service;

import com.ecommerce.panier.model.client.Client;
import com.ecommerce.panier.model.client.TypeClient;
import com.ecommerce.panier.model.produit.TypeProduit;
import com.ecommerce.panier.model.tarification.CleTarification;
import com.ecommerce.panier.repository.TarificationRepository;
import org.springframework.stereotype.Service;

/**
 * Implémentation du service de tarification
 * Calcule les prix selon les règles métier définies
 */
@Service
public class PricingServiceImpl implements PricingService {
    
    private final TypeClientService typeClientService;
    private final TarificationRepository tarificationRepository;
    
    public PricingServiceImpl(TypeClientService typeClientService, 
                             TarificationRepository tarificationRepository) {
        this.typeClientService = typeClientService;
        this.tarificationRepository = tarificationRepository;
    }
    
    @Override
    public double getPrix(TypeProduit typeProduit, Client client) {
        // Validation des paramètres
        if (typeProduit == null) {
            throw new IllegalArgumentException("Le type de produit ne peut pas être null");
        }
        if (client == null) {
            throw new IllegalArgumentException("Le client ne peut pas être null");
        }
        
        // 1. Déterminer le type de client via TypeClientService
        TypeClient typeClient = typeClientService.determineTypeClient(client);
        
        // 2. Créer la clé de tarification
        CleTarification cleTarification = new CleTarification(typeProduit, typeClient);
        
        // 3. Chercher le prix via TarificationRepository
        return tarificationRepository.findPrix(cleTarification)
                .orElseThrow(() -> new IllegalStateException(
                    String.format("Aucun prix configuré pour %s + %s", 
                                typeProduit.getNom(), typeClient.getNom())));
    }
}