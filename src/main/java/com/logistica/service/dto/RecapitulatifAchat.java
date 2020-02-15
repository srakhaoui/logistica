package com.logistica.service.dto;

import java.time.LocalDate;

public class RecapitulatifAchat {
	private LocalDate dateBonCommande;
	private Integer numeroBonCommande;
	private String codeProduit;
	private Double totalQuantiteAchetee;
	private Double totalQuantiteConvertie;
	private Double totalPrixAchat;

	public RecapitulatifAchat(LocalDate dateBonCommande, Integer numeroBonCommande, String codeProduit, Double totalQuantiteAchetee, Double totalQuantiteConvertie, Double totalPrixAchat) {
		this.dateBonCommande = dateBonCommande;
		this.numeroBonCommande = numeroBonCommande;
		this.codeProduit = codeProduit;
		this.totalQuantiteAchetee = totalQuantiteAchetee;
		this.totalQuantiteConvertie = totalQuantiteConvertie;
		this.totalPrixAchat = totalPrixAchat;
	}

	public LocalDate getDateBonCommande() {
		return dateBonCommande;
	}

	public Integer getNumeroBonCommande() {
		return numeroBonCommande;
	}

	public String getCodeProduit() {
		return codeProduit;
	}

	public Double getTotalQuantiteAchetee() {
		return totalQuantiteAchetee;
	}

	public Double getTotalQuantiteConvertie() {
		return totalQuantiteConvertie;
	}

	public Double getTotalPrixAchat() {
		return totalPrixAchat;
	}
}
