package com.ecommerce.panier.model.client;

import java.util.Objects;

//Classe pour les clients particuliers
public class ClientParticulier extends Client {
 private String nom;
 private String prenom;
 
 public ClientParticulier(String idClient, String nom, String prenom) {
     super(idClient);
     this.nom = Objects.requireNonNull(nom, "Le nom ne peut pas être null");
     this.prenom = Objects.requireNonNull(prenom, "Le prénom ne peut pas être null");
 }
  
 public String getNom() {
     return nom;
 }
 
 public String getPrenom() {
     return prenom;
 }
 
 @Override
 public String toString() {
     return String.format("Client particulier: %s %s (ID: %s)", prenom, nom, idClient);
 }
}

