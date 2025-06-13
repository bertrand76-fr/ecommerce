package com.ecommerce.panier.repository;

import java.util.Map;
import java.util.Optional;

import com.ecommerce.panier.model.client.TypeClient;

public interface TypeClientRepository {
	
    Map<String, TypeClient> findAll(); 
    Optional<TypeClient> findByCode(String code);

}
