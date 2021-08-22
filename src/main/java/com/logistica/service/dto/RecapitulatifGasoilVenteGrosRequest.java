package com.logistica.service.dto;

import java.time.LocalDate;

public class RecapitulatifGasoilVenteGrosRequest {
    private long fournisseurId;
    private long acheteurId;
    private long transporteurId;
    private long clientId;
    private LocalDate dateDebut;
    private LocalDate dateFin;


    public long getTransporteurId() {
        return transporteurId;
    }

    public void setTransporteurId(long transporteurId) {
        this.transporteurId = transporteurId;
    }

    public long getAcheteurId() {
        return acheteurId;
    }

    public void setAcheteurId(long acheteurId) {
        this.acheteurId = acheteurId;
    }

    public long getFournisseurId() {
        return fournisseurId;
    }

    public void setFournisseurId(long fournisseurId) {
        this.fournisseurId = fournisseurId;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
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
