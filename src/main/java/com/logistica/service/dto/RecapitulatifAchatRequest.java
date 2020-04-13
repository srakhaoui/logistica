package com.logistica.service.dto;

import java.time.LocalDate;

public class RecapitulatifAchatRequest {

	private Long societeId;
	private Long fournisseurId;
	private LocalDate dateDebut;
	private LocalDate dateFin;

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

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }
}
