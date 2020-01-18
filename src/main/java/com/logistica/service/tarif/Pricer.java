package com.logistica.service.tarif;

import com.logistica.domain.Livraison;

public interface Pricer {
	
	Float price(Livraison livraison);

}
