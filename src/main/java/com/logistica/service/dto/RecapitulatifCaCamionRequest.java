package com.logistica.service.dto;

import java.time.LocalDate;

public class RecapitulatifCaCamionRequest {
    private LocalDate dateDebutLivraison;
    private LocalDate dateFinLivraison;

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
