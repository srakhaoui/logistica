package com.logistica.service.dto;

import java.time.LocalDate;

public class RecapitulatifGasoilGrosRequest {
    private String numeroBonReception;
    private Long fournisseurId;
    private Long acheteurId;
    private Long transporteurId;
    private Long clientId;
    private LocalDate dateDebut;
    private LocalDate dateFin;


    public String getNumeroBonReception() {
        return numeroBonReception;
    }

    public void setNumeroBonReception(String numeroBonReception) {
        this.numeroBonReception = numeroBonReception;
    }

    public long getTransporteurId() {
        return transporteurId;
    }

    public void setTransporteurId(Long transporteurId) {
        this.transporteurId = transporteurId;
    }

    public Long getAcheteurId() {
        return acheteurId;
    }

    public void setAcheteurId(Long acheteurId) {
        this.acheteurId = acheteurId;
    }

    public Long getFournisseurId() {
        return fournisseurId;
    }

    public void setFournisseurId(Long fournisseurId) {
        this.fournisseurId = fournisseurId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
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
