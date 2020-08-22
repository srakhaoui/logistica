package com.logistica.service.dto;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class RecapitulatifEfficaciteChauffeurRequest {
    private Long transporteurId;
    @NotNull
    private LocalDate dateDebut;
    @NotNull
    private LocalDate dateFin;

    public Long getTransporteurId() {
        return transporteurId;
    }

    public void setTransporteurId(Long transporteurId) {
        this.transporteurId = transporteurId;
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
