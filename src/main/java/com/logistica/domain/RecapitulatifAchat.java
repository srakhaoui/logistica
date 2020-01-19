package com.logistica.domain;

import java.time.LocalDate;

public class RecapitulatifAchat {
	private LocalDate dateBonCommande;
	private Integer numeroBonCommande;
	private String codeProduit;
	private Long totalQuantiteAchetee;
	private Long totalQuantiteConvertie;
	private Double totalPrixAchat;

	public RecapitulatifAchat(LocalDate dateBonCommande, Integer numeroBonCommande, String codeProduit, Long totalQuantiteAchetee, Long totalQuantiteConvertie, Double totalPrixAchat) {
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

	public Long getTotalQuantiteAchetee() {
		return totalQuantiteAchetee;
	}

	public Long getTotalQuantiteConvertie() {
		return totalQuantiteConvertie;
	}

	public Double getTotalPrixAchat() {
		return totalPrixAchat;
	}
}
