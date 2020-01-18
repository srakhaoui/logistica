package com.logistica.service.tarif;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class PricerFactory {
	
	@Autowired
	@Qualifier("transport")
	private Pricer transportPricer;
	
	@Autowired
	@Qualifier("vente")
	private Pricer ventePricer;
	
	@Autowired
	@Qualifier("achat")
	private Pricer achatPricer;
	
	public Pricer getPricer(TypePricer typePricer) {
		switch(typePricer) {
		case Transport: return transportPricer;
		case Achat: return achatPricer;
		case Vente: return ventePricer;
		default: throw new IllegalArgumentException(String.format("The %s pricer type is not supported ", typePricer));
		}
	}
}
