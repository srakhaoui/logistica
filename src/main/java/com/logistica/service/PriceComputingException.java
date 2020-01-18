package com.logistica.service;

import com.logistica.service.tarif.TypePricer;

public class PriceComputingException extends RuntimeException {
	
	private TypePricer pricerType;
	
	public PriceComputingException(TypePricer typePricer) {
		super();
		this.pricerType = typePricer;
	}
	
	public TypePricer getType() {
		return pricerType;
	}
}
