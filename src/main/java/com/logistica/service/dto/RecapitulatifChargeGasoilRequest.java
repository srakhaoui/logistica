package com.logistica.service.dto;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class RecapitulatifChargeGasoilRequest {

    private Long societeId;
    @NotNull
    private LocalDate dateDebut;
    @NotNull
    private LocalDate dateFin;

    public Long getSocieteId() {
        return societeId;
    }

    public void setSocieteId(Long societeId) {
        this.societeId = societeId;
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
