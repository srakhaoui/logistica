package com.logistica.service.dto;

import java.time.LocalDateTime;

public class BonGasoilInfo {
    private String matricule;
    private LocalDateTime dateBonGasoil;
    private Float quantiteEnLitre;
    private Float prixUnitaire;
    private Long numeroBonGasoil;
    private Integer kilometrageInitial;
    private Integer kilometrageFinal;
    private String citerne;

    public static BonGasoilInfo newInstance() {
        return new BonGasoilInfo();
    }

    public BonGasoilInfo quantiteEnLitre(Float quantiteEnLitre) {
        this.quantiteEnLitre = quantiteEnLitre;
        return this;
    }

    public BonGasoilInfo dateBonGasoil(LocalDateTime dateBonGasoil) {
        this.dateBonGasoil = dateBonGasoil;
        return this;
    }

    public BonGasoilInfo matricule(String matricule) {
        this.matricule = matricule;
        return this;
    }

    public BonGasoilInfo prixUnitaire(Float prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
        return this;
    }

    public Float getPrixUnitaire() {
        return prixUnitaire;
    }

    public BonGasoilInfo numeroBonGasoil(Long numeroBonGasoil) {
        this.numeroBonGasoil = numeroBonGasoil;
        return this;
    }

    public Long getNumeroBonGasoil() {
        return numeroBonGasoil;
    }

    public BonGasoilInfo kilometrageInitial(Integer kilometrageInitial) {
        this.kilometrageInitial = kilometrageInitial;
        return this;
    }

    public Integer getKilometrageInitial() {
        return kilometrageInitial;
    }

    public BonGasoilInfo kilometrageFinal(Integer kilometrageFinal) {
        this.kilometrageFinal = kilometrageFinal;
        return this;
    }

    public Integer getKilometrageFinal() {
        return kilometrageFinal;
    }

    public BonGasoilInfo citerne(String citerne) {
        this.citerne = citerne;
        return this;
    }

    public String getCiterne() {
        return citerne;
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
