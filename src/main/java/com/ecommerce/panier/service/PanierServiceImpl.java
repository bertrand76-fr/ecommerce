package com.ecommerce.panier.service;

import com.ecommerce.panier.model.client.Client;
import com.ecommerce.panier.model.panier.Panier;
import com.ecommerce.panier.model.produit.TypeProduit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Service pour la gestion des paniers
 * Calcule les coûts et affiche les détails des paniers
 */
@Service
public class PanierServiceImpl implements PanierService {
    
	@Autowired
    private PricingService pricingService;
    
	
    /**
     * Calcule le coût total du panier pour un client donné
     * Utilise PricingService pour obtenir le prix de chaque type de produit
     * 
     * @param panier Le panier à calculer
     * @param client Le client pour lequel calculer le prix
     * @return Le coût total du panier
     * @throws IllegalArgumentException si panier ou client est null
     */
	@Override
    public double calculerCoutTotal(Panier panier, Client client) {
        if (panier == null) {
            throw new IllegalArgumentException("Le panier ne peut pas être null");
        }
        if (client == null) {
            throw new IllegalArgumentException("Le client ne peut pas être null");
        }
        
        if (panier.estVide()) {
            return 0.0;
        }
        
        double total = 0.0;
        
        for (Map.Entry<TypeProduit, Integer> entry : panier.getTypeProduits().entrySet()) {
            TypeProduit typeProduit = entry.getKey();
            Integer quantite = entry.getValue();
            
            // Obtenir le prix unitaire via PricingService
            double prixUnitaire = pricingService.getPrix(typeProduit, client);
            
            // Calculer le sous-total pour ce type de produit
            double sousTotal = prixUnitaire * quantite;
            total += sousTotal;
        }
        
        return total;
    }
    
}