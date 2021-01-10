package com.logistica.service.dto;

import java.time.LocalDate;
import java.util.List;

public class StatistiquesTauxRentabiliteRequest {

    private Long societeId;
    private List<String> matriculesToInclude;
    private List<String> matriculesToExclude;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private boolean withEvolutionChiffreAffaire;
    private boolean withEvolutionChargeGasoil;
    private boolean withEvolutionTauxRentabilite;
    private boolean withTauxRentabiliteParMatricule;

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

    public boolean isWithEvolutionChiffreAffaire() {
        return withEvolutionChiffreAffaire;
    }

    public void setWithEvolutionChiffreAffaire(boolean withEvolutionChiffreAffaire) {
        this.withEvolutionChiffreAffaire = withEvolutionChiffreAffaire;
    }

    public boolean isWithEvolutionChargeGasoil() {
        return withEvolutionChargeGasoil;
    }

    public void setWithEvolutionChargeGasoil(boolean withEvolutionChargeGasoil) {
        this.withEvolutionChargeGasoil = withEvolutionChargeGasoil;
    }

    public boolean isWithEvolutionTauxRentabilite() {
        return withEvolutionTauxRentabilite;
    }

    public void setWithEvolutionTauxRentabilite(boolean withEvolutionTauxRentabilite) {
        this.withEvolutionTauxRentabilite = withEvolutionTauxRentabilite;
    }

    public boolean isWithTauxRentabiliteParMatricule() {
        return withTauxRentabiliteParMatricule;
    }

    public void setWithTauxRentabiliteParMatricule(boolean withTauxRentabiliteParMatricule) {
        this.withTauxRentabiliteParMatricule = withTauxRentabiliteParMatricule;
    }
}
