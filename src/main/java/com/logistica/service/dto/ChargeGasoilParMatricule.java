package com.logistica.service.dto;

public class ChargeGasoilParMatricule {
    private Double charge;
    private String matricule;

    public ChargeGasoilParMatricule(String matricule, Double charge) {
        this.matricule = matricule;
        this.charge = charge;
    }

    public Double getCharge() {
        return charge;
    }

    public void setCharge(Double charge) {
        this.charge = charge;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }
}
