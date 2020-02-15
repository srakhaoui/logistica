package com.logistica.service.dto;

import java.time.LocalDate;

public class RecapitulatifAchatRequest {

	private Long societeId;
	private Long fournisseurId;
	private LocalDate dateDebutBonCommande;
	private LocalDate dateFinBonCommande;

	public Long getSocieteId() {
		return societeId;
	}
	public void setSocieteId(Long societeId) {
		this.societeId = societeId;
	}
	public Long getFournisseurId() {
		return fournisseurId;
	}
	public void setFournisseurId(Long fournisseurId) {
		this.fournisseurId = fournisseurId;
	}

    public LocalDate getDateDebutBonCommande() {
        return dateDebutBonCommande;
    }

    public void setDateDebutBonCommande(LocalDate dateDebutBonCommande) {
        this.dateDebutBonCommande = dateDebutBonCommande;
    }

    public LocalDate getDateFinBonCommande() {
        return dateFinBonCommande;
    }

    public void setDateFinBonCommande(LocalDate dateFinBonCommande) {
        this.dateFinBonCommande = dateFinBonCommande;
    }
}
