package com.logistica.service.dto;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public class ChargeGasoilRequest {

    private Long societeId;
    private List<String> matriculeToInclude;
    private List<String> matriculesToExclude;
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

    public List<String> getMatriculeToInclude() {
        return matriculeToInclude;
    }

    public void setMatriculeToInclude(List<String> matriculeToInclude) {
        this.matriculeToInclude = matriculeToInclude;
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

    public List<String> getMatriculesToExclude() {
        return matriculesToExclude;
    }

    public void setMatriculesToExclude(List<String> matriculesToExclude) {
        this.matriculesToExclude = matriculesToExclude;
    }
}
