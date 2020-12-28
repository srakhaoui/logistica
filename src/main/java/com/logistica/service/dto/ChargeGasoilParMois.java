package com.logistica.service.dto;

public class ChargeGasoilParMois {
    private Double charge;
    private Integer mois;
    private Integer annee;

    public ChargeGasoilParMois(Integer annee, Integer mois, Double charge) {
        this.annee = annee;
        this.mois = mois;
        this.charge = charge;
    }

    public Double getCharge() {
        return charge;
    }

    public Float getChargeGasoilAsFloat() {
        return charge.floatValue();
    }

    public void setCharge(Double charge) {
        this.charge = charge;
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
