package com.logistica.service.dto;

import java.util.List;

public class RecapitulatifVentesClient {

    private List<RecapitulatifClient> recapitulatifClients;
    private Double montantFacturationMax;
    private Float montantFacturationMin;

    public RecapitulatifVentesClient(List<RecapitulatifClient> recapitulatifClients, Double montantFacturationMax, Float montantFacturationMin) {
        this.recapitulatifClients = recapitulatifClients;
        this.montantFacturationMax = montantFacturationMax;
        this.montantFacturationMin = montantFacturationMin;
    }

    public List<RecapitulatifClient> getRecapitulatifClients() {
        return recapitulatifClients;
    }

    public void setRecapitulatifClients(List<RecapitulatifClient> recapitulatifClients) {
        this.recapitulatifClients = recapitulatifClients;
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

    public static RecapitulatifVentesClient from(RecapitulatifVentesClientPage recapitulatifVentesClientPage){
        return new RecapitulatifVentesClient(recapitulatifVentesClientPage.getRecapitulatifClientPage().getContent(), recapitulatifVentesClientPage.getMontantFacturationMax(), recapitulatifVentesClientPage.getMontantFacturationMin());
    }
}
