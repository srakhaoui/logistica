package com.logistica.service.dto;

import org.springframework.data.domain.Pageable;

public class RecapitulatifAchatRequest {
	
	private Long societeId;
	private Long fournisseurId;
	
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
}
