package com.logistica.service.dto;

import java.time.LocalDateTime;

public class BonGasoilInfo {
    private String matricule;
    private LocalDateTime dateBonGasoil;
    private Float quantiteEnLitre;

    public BonGasoilInfo(String matricule, LocalDateTime dateBonGasoil, Float quantiteEnLitre) {
        this.matricule = matricule;
        this.dateBonGasoil = dateBonGasoil;
        this.quantiteEnLitre = quantiteEnLitre;
    }

    public String getMatricule() {
        return matricule;
    }

    public LocalDateTime getDateBonGasoil() {
        return dateBonGasoil;
    }

    public Float getQuantiteEnLitre() {
        return quantiteEnLitre;
    }
}
