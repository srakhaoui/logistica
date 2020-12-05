package com.logistica.service.dto;

public class ChiffreAffaireParMois {
    private Double chiffreAffaire;
    private Integer mois;
    private Integer annee;

    public ChiffreAffaireParMois(Integer annee, Integer mois, Double chiffreAffaire) {
        this.annee = annee;
        this.mois = mois;
        this.chiffreAffaire = chiffreAffaire;
    }

    public Double getChiffreAffaire() {
        return chiffreAffaire;
    }

    public Float getChiffreAffaireAsFloat() {
        return chiffreAffaire.floatValue();
    }

    public void setChiffreAffaire(Double chiffreAffaire) {
        this.chiffreAffaire = chiffreAffaire;
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
