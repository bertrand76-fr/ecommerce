package com.ecommerce.panier.model.produit;

import java.util.Objects;

//Classe pour représenter un produit physique, non utilisé pour la tarification
public class Produit {
	private TypeProduit typeProduit;
	private String identifiantStock;

	public Produit(TypeProduit typeProduit) {
		this.typeProduit = Objects.requireNonNull(typeProduit, "Le type de produit ne peut pas être null");
	}

	public TypeProduit getTypeProduit() {
		return typeProduit;
	}

		
	public String getIdentifiantStock() {
		return identifiantStock;
	}

	public void setIdentifiantStock(String identifiantStock) {
		this.identifiantStock = identifiantStock;
	}

	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produit other = (Produit) obj;
		return Objects.equals(identifiantStock, other.identifiantStock)
				&& Objects.equals(typeProduit, other.typeProduit);
	}

	@Override
	public int hashCode() {
		return Objects.hash(identifiantStock, typeProduit);
	}

	@Override
	public String toString() {
		return "Produit [typeProduit=" + typeProduit + ", identifiantStock=" + identifiantStock + "]";
	}
}
