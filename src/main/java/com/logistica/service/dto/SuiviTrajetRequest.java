package com.logistica.service.dto;

import java.time.LocalDate;

public class SuiviTrajetRequest {

    private Long societeId;
    private LocalDate dateDebutLivraison;
    private LocalDate dateFinLivraison;

    public Long getSocieteId() {
        return societeId;
    }

    public void setSocieteId(Long societeId) {
        this.societeId = societeId;
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
