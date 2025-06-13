package com.ecommerce.panier.service;

import com.ecommerce.panier.model.client.Client;
import com.ecommerce.panier.model.client.TypeClient;

public interface TypeClientService {
	TypeClient determineTypeClient(Client client);
}
