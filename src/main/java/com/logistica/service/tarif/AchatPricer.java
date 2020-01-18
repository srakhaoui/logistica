package com.logistica.service.tarif;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.logistica.domain.Fournisseur;
import com.logistica.domain.Livraison;
import com.logistica.domain.Produit;
import com.logistica.domain.enumeration.Unite;
import com.logistica.service.TarifAchatService;

@Service
@Qualifier("achat")
public class AchatPricer implements Pricer {
	
	@Autowired
	private TarifAchatService TarifAchatService;

	@Override
	public Float price(Livraison livraison) {
		Long fournisseurId = Optional.ofNullable(livraison.getFournisseur()).map(Fournisseur::getId).orElseThrow(() -> new IllegalArgumentException("Le fournisseur est obligatoire pour calculer le prix"));
		Long produitId = Optional.ofNullable(livraison.getProduit()).map(Produit::getId).orElseThrow(() -> new IllegalArgumentException("Le produit est obligatoire pour calculer le prix"));
		Unite unite = Optional.ofNullable(livraison.getUniteVente()).orElseThrow(() -> new IllegalArgumentException("L'unité est obligatoire pour calculer le prix"));
		return TarifAchatService.findPrixByFournisseurProduitAndUnite(fournisseurId, produitId, unite);
	}

}
