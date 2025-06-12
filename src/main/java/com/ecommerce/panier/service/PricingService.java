package com.ecommerce.panier.service;

import com.ecommerce.panier.model.client.Client;
import com.ecommerce.panier.model.produit.TypeProduit;

/**
 * Interface pour le service de tarification
 * Calcule le prix d'un type de produit selon le type de client
 */
public interface PricingService {
    
    /**
     * Calcule le prix d'un type de produit pour un client donné
     * Applique les règles de tarification selon le type de client
     * 
     * @param typeProduit Le type de produit dont on veut calculer le prix
     * @param client Le client pour lequel calculer le prix  
     * @return Le prix calculé selon les règles de tarification
     * @throws IllegalArgumentException si typeProduit ou client est null
     */
    double getPrix(TypeProduit typeProduit, Client client);
}