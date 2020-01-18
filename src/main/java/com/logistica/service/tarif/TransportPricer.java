package com.logistica.service.tarif;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.logistica.domain.Client;
import com.logistica.domain.Livraison;
import com.logistica.domain.Produit;
import com.logistica.domain.Trajet;
import com.logistica.domain.enumeration.Unite;
import com.logistica.service.TarifTransportService;

@Service
@Qualifier("transport")
public class TransportPricer implements Pricer {
	
	@Autowired
	private TarifTransportService TarifTransportService;

	@Override
	public Float price(Livraison livraison) {
		Long clientId = Optional.ofNullable(livraison.getClient()).map(Client::getId).orElseThrow(() -> new IllegalArgumentException("Le client est obligatoire pour calculer le prix"));
		Long produitId = Optional.ofNullable(livraison.getProduit()).map(Produit::getId).orElseThrow(() -> new IllegalArgumentException("Le produit est obligatoire pour calculer le prix"));
		Unite unite = Optional.ofNullable(livraison.getUniteVente()).orElseThrow(() -> new IllegalArgumentException("L'unité est obligatoire pour calculer le prix"));
		Long trajetId = Optional.ofNullable(livraison.getTrajet()).map(Trajet::getId).orElseThrow(() -> new IllegalArgumentException("Le trajet est obligatoire pour calculer le prix"));
		
		return TarifTransportService.findPrixByClientProduitUniteAndTrajet(clientId, produitId, unite, trajetId);
	}
}
