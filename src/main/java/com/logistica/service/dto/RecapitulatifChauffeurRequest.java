package com.logistica.service.dto;

import java.time.LocalDate;

public class RecapitulatifChauffeurRequest {
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private Long idTransporteur;

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

    public Long getIdTransporteur() {
        return idTransporteur;
    }

    public void setIdTransporteur(Long idTransporteur) {
        this.idTransporteur = idTransporteur;
    }
}
