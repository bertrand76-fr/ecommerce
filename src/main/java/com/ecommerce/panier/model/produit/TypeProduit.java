package com.ecommerce.panier.model.produit;

import java.util.Objects;

//Classe pour représenter un type de produit
public class TypeProduit {
 private String nom;
 
 public TypeProduit(String nom) {
     this.nom = Objects.requireNonNull(nom, "Le nom du type de produit ne peut pas être null");
 }
 
 public String getNom() {
     return nom;
 }
 
 @Override
 public boolean equals(Object obj) {
     if (this == obj) return true;
     if (obj == null || getClass() != obj.getClass()) return false;
     TypeProduit that = (TypeProduit) obj;
     return Objects.equals(nom, that.nom);
 }
 
 @Override
 public int hashCode() {
     return Objects.hash(nom);
 }
 
 @Override
 public String toString() {
     return nom;
 }
}