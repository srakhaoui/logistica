package com.logistica.service.dto;

public class MontantFacturationMaxAndMin {
    private Double montantMax;
    private Float montantMin;

    public MontantFacturationMaxAndMin(Double montantMax, Float montantMin){
        this.montantMax = montantMax;
        this.montantMin = montantMin;
    }

    public Double getMontantMax() {
        return montantMax;
    }

    public void setMontantMax(Double montantMax) {
        this.montantMax = montantMax;
    }

    public Float getMontantMin() {
        return montantMin;
    }

    public void setMontantMin(Float montantMin) {
        this.montantMin = montantMin;
    }
}
