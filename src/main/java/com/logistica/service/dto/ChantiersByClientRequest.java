package com.logistica.service.dto;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class ChantiersByClientRequest {
    @NotNull
    private Long clientId;
    @NotNull
    private LocalDate dateDebut;
    @NotNull
    private LocalDate dateFin;

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
