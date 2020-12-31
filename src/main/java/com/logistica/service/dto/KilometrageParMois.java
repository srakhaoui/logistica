package com.logistica.service.dto;

public class KilometrageParMois {
    private Long kilometrage;
    private Integer mois;
    private Integer annee;

    public KilometrageParMois(Integer annee, Integer mois, Long kilometrage) {
        this.annee = annee;
        this.mois = mois;
        this.kilometrage = kilometrage;
    }

    public Long getKilometrage() {
        return kilometrage;
    }

    public Float getKilometrageAsFloat() {
        return kilometrage.floatValue();
    }

    public void setKilometrage(Long kilometrage) {
        this.kilometrage = kilometrage;
    }

    public Integer getMois() {
        return mois;
    }

    public void setMois(Integer mois) {
        this.mois = mois;
    }

    public Integer getAnnee() {
        return annee;
    }

    public void setAnnee(Integer annee) {
        this.annee = annee;
    }
}
