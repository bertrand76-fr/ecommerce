package com.ecommerce.panier.repository;

import java.util.Map;
import java.util.Optional;

import com.ecommerce.panier.model.produit.TypeProduit;

public interface TypeProduitRepository {

    Map<String, TypeProduit> findAll();
    Optional<TypeProduit> findByCode(String code);
}
