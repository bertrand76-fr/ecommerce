package com.ecommerce.panier.service;

import com.ecommerce.panier.model.client.Client;
import com.ecommerce.panier.model.client.ClientParticulier;
import com.ecommerce.panier.model.client.ClientProfessionnel;
import com.ecommerce.panier.model.client.TypeClient;
import com.ecommerce.panier.repository.TypeClientRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Service pour la gestion des types de clients
 * Détermine automatiquement le type selon les règles métier
 */
@Service
public class TypeClientServiceImpl implements TypeClientService {
    
	@Autowired
    private TypeClientRepository typeClientRepository;
    
    // Seuil pour différencier les professionnels (10 millions d'euros)
    private static final double SEUIL_GROS_PROFESSIONNEL = 10_000_000.0;
    
    // Constantes pour les noms de types de clients
    private static final String PARTICULIER = "PARTICULIER";
    private static final String PETITCLIENT = "PROFESSIONNEL_PETIT";
    private static final String GROSCLIENT = "PROFESSIONNEL_GROS";
    
    
    /**
     * Détermine automatiquement le type d'un client selon les règles métier :
     * - ClientParticulier → PARTICULIER
     * - ClientProfessionnel avec CA < 10M€ → PROFESSIONNEL_PETIT
     * - ClientProfessionnel avec CA ≥ 10M€ → PROFESSIONNEL_GROS
     * 
     * @param client Le client à analyser
     * @return Le TypeClient correspondant
     * @throws IllegalArgumentException si le client est null ou d'un type inconnu
     */
    @Override
    public TypeClient determineTypeClient(Client client) {
        if (client == null) {
            throw new IllegalArgumentException("Le client ne peut pas être null");
        }
        
        if (client instanceof ClientParticulier) {
            return getTypeByName(PARTICULIER);
        }
        
        if (client instanceof ClientProfessionnel clientPro) {
            double ca = clientPro.getChiffresAffaires();
            
            if (ca >= SEUIL_GROS_PROFESSIONNEL) {
                return getTypeByName(GROSCLIENT);
            } else {
                return getTypeByName(PETITCLIENT);
            }
        }
        
        throw new IllegalArgumentException("Type de client non supporté: " + client.getClass().getSimpleName());
    }
    
    /**
     * Récupère un TypeClient par son nom (clé JSON)
     * @param name Le nom du type (PARTICULIER, PROFESSIONNEL_PETIT, PROFESSIONNEL_GROS)
     * @return Le TypeClient correspondant
     * @throws IllegalStateException si le type n'est pas configuré
     */
    private TypeClient getTypeByName(String name) {
        return typeClientRepository.findByCode(name)
                .orElseThrow(() -> new IllegalStateException(
                    "Type de client non configuré: " + name + 
                    ". Vérifiez la configuration JSON."));
    }
}