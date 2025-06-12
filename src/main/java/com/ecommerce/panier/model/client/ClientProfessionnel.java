package com.ecommerce.panier.model.client;

import java.util.Objects;
import java.util.Optional;

// Classe pour les clients professionnels
public class ClientProfessionnel extends Client {
	private String raisonSociale;
	private Optional<String> numeroTVA;
	private String siren;
	private double chiffresAffaires;

	public ClientProfessionnel(String idClient, String raisonSociale, String siren, double chiffresAffaires) {
		super(idClient);
		this.raisonSociale = Objects.requireNonNull(raisonSociale, "La raison sociale ne peut pas être null");
		this.siren = Objects.requireNonNull(siren, "Le SIREN ne peut pas être null");
		this.chiffresAffaires = chiffresAffaires;
		this.numeroTVA = Optional.empty();
	}

	public ClientProfessionnel(String idClient, String raisonSociale, String numeroTVA, String siren, double chiffresAffaires) {
		super(idClient);
		this.raisonSociale = Objects.requireNonNull(raisonSociale, "La raison sociale ne peut pas être null");
		this.siren = Objects.requireNonNull(siren, "Le SIREN ne peut pas être null");
		this.chiffresAffaires = chiffresAffaires;
		this.numeroTVA = Optional.ofNullable(numeroTVA);
	}


	public String getRaisonSociale() {
		return raisonSociale;
	}

	public Optional<String> getNumeroTVA() {
		return numeroTVA;
	}

	public String getSiren() {
		return siren;
	}

	public double getChiffresAffaires() {
		return chiffresAffaires;
	}

	@Override
	public String toString() {
		String tvaInfo = numeroTVA.map(tva -> ", TVA: " + tva).orElse("");
		return String.format("Client professionnel: %s (SIREN: %s%s, CA: %.2f€, ID: %s)", 
				raisonSociale, siren, tvaInfo, chiffresAffaires, idClient);
	}
}


