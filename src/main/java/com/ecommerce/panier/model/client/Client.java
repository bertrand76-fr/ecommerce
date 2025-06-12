package com.ecommerce.panier.model.client;

import java.util.Objects;

//Classe abstraite pour représenter un client
abstract public class Client {
 protected String idClient;
 
 public Client(String idClient) {
     this.idClient = Objects.requireNonNull(idClient, "L'ID client ne peut pas être null");
 }
 
 public String getIdClient() {
     return idClient;
 }
 
}