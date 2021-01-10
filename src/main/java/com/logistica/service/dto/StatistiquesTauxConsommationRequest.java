package com.logistica.service.dto;

import java.time.LocalDate;
import java.util.List;

public class StatistiquesTauxConsommationRequest {

    private Long societeId;
    private List<String> matriculesToInclude;
    private List<String> matriculesToExclude;
    private LocalDate dateDebut;
    private LocalDate dateFin;

    public Long getSocieteId() {
        return societeId;
    }

    public void setSocieteId(Long societeId) {
        this.societeId = societeId;
    }

    public List<String> getMatriculesToInclude() {
        return matriculesToInclude;
    }

    public void setMatriculesToInclude(List<String> matriculesToInclude) {
        this.matriculesToInclude = matriculesToInclude;
    }

    public List<String> getMatriculesToExclude() {
        return matriculesToExclude;
    }

    public void setMatriculesToExclude(List<String> matriculesToExclude) {
        this.matriculesToExclude = matriculesToExclude;
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
