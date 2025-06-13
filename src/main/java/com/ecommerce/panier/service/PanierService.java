package com.ecommerce.panier.service;

import com.ecommerce.panier.model.client.Client;
import com.ecommerce.panier.model.panier.Panier;

public interface PanierService {

	double calculerCoutTotal(Panier panier, Client client);
}
