package com.ecommerce.panier.runner;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ecommerce.panier.model.client.Client;
import com.ecommerce.panier.model.client.ClientParticulier;
import com.ecommerce.panier.model.client.ClientProfessionnel;
import com.ecommerce.panier.model.panier.Panier;
import com.ecommerce.panier.model.produit.TypeProduit;
import com.ecommerce.panier.repository.TypeProduitRepository;
import com.ecommerce.panier.service.PanierService;
import com.ecommerce.panier.service.PricingService;

@Component
public class DemoRunner implements CommandLineRunner {
	
    @Autowired
    private PanierService panierService;

    
    @Autowired
    private PricingService pricingService;
    
    @Autowired
    private TypeProduitRepository typeProduitRepository;



    @Override
    public void run(String... args) {
        System.out.println("==== DEBUT DEMO PANIER ====");
        
        // Récupération des types de produits via repository
        System.out.println("Recuperation des types de produit");
        
        TypeProduit telephoneHautGamme = typeProduitRepository.findByCode("TELEPHONE_HAUT_GAMME")
                .orElseThrow(() -> new RuntimeException("TypeProduit TELEPHONE_HAUT_GAMME non trouvé"));
        TypeProduit telephoneMoyenGamme = typeProduitRepository.findByCode("TELEPHONE_MOYEN_GAMME")
                .orElseThrow(() -> new RuntimeException("TypeProduit TELEPHONE_MOYEN_GAMME non trouvé"));
        TypeProduit laptop = typeProduitRepository.findByCode("LAPTOP")
                .orElseThrow(() -> new RuntimeException("TypeProduit LAPTOP non trouvé"));
        
        // Création du panier avec les 3 types de produits 
        
        System.out.println("Creation d'un panier de 1 telephone haut de gamme, 2 moyen gamme, 3 laptops");
        Panier panier = new Panier();
        panier.ajouterTypeProduitQuantite(telephoneHautGamme, 1);
        panier.ajouterTypeProduitQuantite(telephoneMoyenGamme, 2);
        panier.ajouterTypeProduitQuantite(laptop, 3);
        
        System.out.println("Contenu du panier: " + panier);
        
        
        // Création des 3 types de clients et affichage du prix de panier
        System.out.println("Panier pour client particulier" );
        Client clientParticulier = new ClientParticulier("C001", "Dupont", "Jean");
        afficherPanier(panier, clientParticulier);        
        
        System.out.println("Panier pour client professionnel petit" );
        Client clientProfessionnelPetit = new ClientProfessionnel("C002", "Petite SARL", "123456789", 5_000_000); // CA < 10M€
        afficherPanier(panier, clientProfessionnelPetit);        
        
        System.out.println("Panier pour client professionnel gros" );
        Client clientProfessionnelGros = new ClientProfessionnel("C003", "Grosse SA", "987654321", 50_000_000); // CA > 10M€
        afficherPanier(panier, clientProfessionnelGros);
        
        System.out.println("==== FIN DE DEMO PANIER ====");
    }
    
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
        
        
        for (Map.Entry<TypeProduit, Integer> entry : panier.getTypeProduits().entrySet()) {
            TypeProduit typeProduit = entry.getKey();
            Integer quantite = entry.getValue();
            
            // Obtenir le prix unitaire via PricingService
            double prixUnitaire = pricingService.getPrix(typeProduit, client);
            
            // Calculer le sous-total pour ce type de produit
            double sousTotal = prixUnitaire * quantite;
            
            // Afficher la ligne
            System.out.printf("- %s x%d : %.2f€ x %d = %.2f€%n", 
                            typeProduit.getNom(), quantite, prixUnitaire, quantite, sousTotal);
        }
        
        double total = panierService.calculerCoutTotal(panier, client);
        
        System.out.println();
        System.out.printf("TOTAL : %.2f€%n", total);
        System.out.println("========================");
    }

   
    
}