package com.logistica.service.dto;

import com.logistica.domain.enumeration.Unite;

import java.time.LocalDate;

public class RecapitulatifFacturationRequest {
    private Long societeId;
    private Boolean facture;
    private Long clientId;
    private LocalDate dateDebutLivraison;
    private LocalDate dateFinLivraison;

    public Long getSocieteId() {
        return societeId;
    }

    public void setSocieteId(Long societeId) {
        this.societeId = societeId;
    }

    public Boolean getFacture() {
        return facture;
    }

    public void setFacture(Boolean facture) {
        this.facture = facture;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
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
