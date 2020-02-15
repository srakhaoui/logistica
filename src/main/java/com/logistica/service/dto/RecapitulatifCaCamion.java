package com.logistica.service.dto;

import com.logistica.domain.enumeration.Unite;

public class RecapitulatifCaCamion {
    private String camion;
    private Unite uniteVente;
    private Double totalQuantiteVendue;
    private Double totalPrixVente;

    public RecapitulatifCaCamion(String camion, Unite uniteVente, Double totalQuantiteVendue, Double totalPrixVente){
        this.camion = camion;
        this.uniteVente = uniteVente;
        this.totalQuantiteVendue = totalQuantiteVendue;
        this.totalPrixVente = totalPrixVente;
    }

    public String getCamion() {
        return camion;
    }

    public Unite getUniteVente() {
        return uniteVente;
    }

    public Double getTotalQuantiteVendue() {
        return totalQuantiteVendue;
    }

    public Double getTotalPrixVente() {
        return totalPrixVente;
    }
}
