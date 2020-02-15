package com.logistica.service.dto;

import com.logistica.domain.enumeration.TypeLivraison;

import java.time.LocalDate;

public class RecapitulatifClientRequest {
    private Long societeId;
    private Boolean facture;
    private TypeLivraison typeLivraison;
    private LocalDate dateDebutLivraison;
    private LocalDate dateFinLivraison;

    public Long getSocieteId() {
        return societeId;
    }

    public void setSocieteId(Long societeId) {
        this.societeId = societeId;
    }

    public Boolean isFacture() {
        return facture;
    }

    public void setFacture(Boolean facture) {
        this.facture = facture;
    }

    public TypeLivraison getTypeLivraison() {
        return typeLivraison;
    }

    public void setTypeLivraison(TypeLivraison typeLivraison) {
        this.typeLivraison = typeLivraison;
    }

    public LocalDate getDateDebutLivraison() {
        return dateDebutLivraison;
    }

    public void setDateDebutLivraison(LocalDate dateDebutLivraison) {
        this.dateDebutLivraison = dateDebutLivraison;
    }

    public LocalDate getDateFinLivraison() {
        return dateFinLivraison;
    }

    public void setDateFinLivraison(LocalDate dateFinLivraison) {
        this.dateFinLivraison = dateFinLivraison;
    }
}
