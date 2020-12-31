package com.logistica.service.dto;

public class LitrageParMois {
    private Double litrage;
    private Integer mois;
    private Integer annee;

    public LitrageParMois(Integer annee, Integer mois, Double litrage) {
        this.annee = annee;
        this.mois = mois;
        this.litrage = litrage;
    }

    public Double getLitrage() {
        return litrage;
    }

    public Float getLitrageAsFloat() {
        return litrage.floatValue();
    }

    public void setLitrage(Double litrage) {
        this.litrage = litrage;
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
