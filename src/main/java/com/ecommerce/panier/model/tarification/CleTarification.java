package com.ecommerce.panier.model.tarification;

import java.util.Objects;

import com.ecommerce.panier.model.client.TypeClient;
import com.ecommerce.panier.model.produit.TypeProduit;

//Classe pour représenter une clé de tarification
public class CleTarification {
	private TypeProduit typeProduit;
	private TypeClient typeClient;

	public CleTarification(TypeProduit typeProduit, TypeClient typeClient) {
		this.typeProduit = Objects.requireNonNull(typeProduit, "Le type de produit ne peut pas être null");
		this.typeClient = Objects.requireNonNull(typeClient, "Le type de client ne peut pas être null");
	}

	public TypeProduit getTypeProduit() {
		return typeProduit;
	}

	public TypeClient getTypeClient() {
		return typeClient;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		CleTarification that = (CleTarification) obj;
		return Objects.equals(typeProduit, that.typeProduit) && 
				Objects.equals(typeClient, that.typeClient);
	}

	@Override
	public int hashCode() {
		return Objects.hash(typeProduit, typeClient);
	}

	@Override
	public String toString() {
		return String.format("CleTarification{produit=%s, client=%s}", 
				typeProduit.getNom(), typeClient.getNom());
	}
}
