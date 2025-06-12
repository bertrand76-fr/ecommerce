package com.ecommerce.panier.model.panier;

import java.util.HashMap;
import java.util.Map;
import com.ecommerce.panier.model.produit.TypeProduit;

//Classe pour représenter le panier
public class Panier {
	private Map<TypeProduit, Integer> typeProduits;

	public Panier() {
		this.typeProduits = new HashMap<>();
	}
		
	public Map<TypeProduit, Integer> getTypeProduits() {
		return typeProduits;
	}


	public void ajouterTypeProduitQuantite(TypeProduit typeProduit, int quantite) {
		if (quantite <= 0) {
			throw new IllegalArgumentException("La quantité doit être positive");
		}
		typeProduits.put(typeProduit, typeProduits.getOrDefault(typeProduit, 0) + quantite);
	}
	
    // Réduire la quantité (ou supprimer si quantité actualisee <= 0)
    public void retirerTypeProduitQuantite(TypeProduit typeProduit, int quantite) {
        if (quantite <= 0) {
            throw new IllegalArgumentException("La quantité à retirer doit être positive");
        }
        
        Integer quantiteActuelle = typeProduits.get(typeProduit);
        if (quantiteActuelle == null) {
            throw new IllegalArgumentException("Ce type de produit n'est pas dans le panier");
        }
        
        int nouvelleQuantite = quantiteActuelle - quantite;
        if (nouvelleQuantite <= 0) {
            typeProduits.remove(typeProduit);  // Supprime si plus rien
        } else {
            typeProduits.put(typeProduit, nouvelleQuantite);
        }
    }

    // Supprimer complètement un type de produit
    public void supprimerTypeProduit(TypeProduit typeProduit) {
        typeProduits.remove(typeProduit);
    }
    
    // Vider complètement le panier
    public void vider() {
        typeProduits.clear();
    }
    
    // Vérifier si le panier est vide
    public boolean estVide() {
        return typeProduits.isEmpty();
    }
}