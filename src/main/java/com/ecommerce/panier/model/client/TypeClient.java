package com.ecommerce.panier.model.client;

import java.util.Objects;

//Classe pour représenter un type de client
public class TypeClient {
 private String nom;
 
 public TypeClient(String nom) {
     this.nom = Objects.requireNonNull(nom, "Le nom du type de client ne peut pas être null");
 }
 
 public String getNom() {
     return nom;
 }
 
 @Override
 public boolean equals(Object obj) {
     if (this == obj) return true;
     if (obj == null || getClass() != obj.getClass()) return false;
     TypeClient that = (TypeClient) obj;
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