package com.ecommerce.panier.repository;

import java.util.Map;
import java.util.Optional;

import com.ecommerce.panier.model.tarification.CleTarification;

public interface TarificationRepository {
    public Map<CleTarification, Double> findAll();
    public Optional<Double> findPrix(CleTarification cle);
}
