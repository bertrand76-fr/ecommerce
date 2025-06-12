package com.ecommerce.panier.service;

import com.ecommerce.panier.model.client.Client;
import com.ecommerce.panier.model.panier.Panier;
import com.ecommerce.panier.model.produit.TypeProduit;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Service pour la gestion des paniers
 * Calcule les coûts et affiche les détails des paniers
 */
@Service
public class PanierService {
    
    private final PricingService pricingService;
    
    public PanierService(PricingService pricingService) {
        this.pricingService = pricingService;
    }
    
    /**
     * Calcule le coût total du panier pour un client donné
     * Utilise PricingService pour obtenir le prix de chaque type de produit
     * 
     * @param panier Le panier à calculer
     * @param client Le client pour lequel calculer le prix
     * @return Le coût total du panier
     * @throws IllegalArgumentException si panier ou client est null
     */
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
    
    /**
     * Affiche le détail du panier avec prix pour un client donné
     * Montre chaque ligne (produit + quantité + prix unitaire + sous-total) + total final
     * 
     * @param panier Le panier à afficher
     * @param client Le client pour lequel calculer les prix
     * @throws IllegalArgumentException si panier ou client est null
     */
    public void afficherPanier(Panier panier, Client client) {
        if (panier == null) {
            throw new IllegalArgumentException("Le panier ne peut pas être null");
        }
        if (client == null) {
            throw new IllegalArgumentException("Le client ne peut pas être null");
        }
        
        System.out.println("=== DÉTAIL DU PANIER ===");
        System.out.println("Client: " + client.toString());
        System.out.println();
        
        if (panier.estVide()) {
            System.out.println("Le panier est vide.");
            return;
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
            
            // Afficher la ligne
            System.out.printf("- %s x%d : %.2f€ x %d = %.2f€%n", 
                            typeProduit.getNom(), quantite, prixUnitaire, quantite, sousTotal);
        }
        
        System.out.println();
        System.out.printf("TOTAL : %.2f€%n", total);
        System.out.println("========================");
    }
}