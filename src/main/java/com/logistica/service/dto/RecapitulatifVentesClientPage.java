package com.logistica.service.dto;

import org.springframework.data.domain.Page;

public class RecapitulatifVentesClientPage {

    private Page<RecapitulatifClient> recapitulatifClientPage;
    private Double montantFacturationMax;
    private Float montantFacturationMin;

    public RecapitulatifVentesClientPage(Page<RecapitulatifClient> recapitulatifClientPage, Double montantFacturationMax, Float montantFacturationMin) {
        this.recapitulatifClientPage = recapitulatifClientPage;
        this.montantFacturationMax = montantFacturationMax;
        this.montantFacturationMin = montantFacturationMin;
    }

    public Page<RecapitulatifClient> getRecapitulatifClientPage() {
        return recapitulatifClientPage;
    }

    public void setRecapitulatifClientPage(Page<RecapitulatifClient> recapitulatifClientPage) {
        this.recapitulatifClientPage = recapitulatifClientPage;
    }

    public Double getMontantFacturationMax() {
        return montantFacturationMax;
    }

    public void setMontantFacturationMax(Double montantFacturationMax) {
        this.montantFacturationMax = montantFacturationMax;
    }

    public Float getMontantFacturationMin() {
        return montantFacturationMin;
    }

    public void setMontantFacturationMin(Float montantFacturationMin) {
        this.montantFacturationMin = montantFacturationMin;
    }
}
