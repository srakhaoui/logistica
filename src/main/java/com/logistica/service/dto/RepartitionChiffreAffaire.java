package com.logistica.service.dto;

public class RepartitionChiffreAffaire {

    private Float chiffreAffaire;
    private Courbe<String, Float> repartition;

    public RepartitionChiffreAffaire() {
        chiffreAffaire = 0.0F;
        repartition = new Courbe<>();
    }

    public Float getChiffreAffaire() {
        return chiffreAffaire;
    }

    public void setChiffreAffaire(Float chiffreAffaire) {
        this.chiffreAffaire = chiffreAffaire;
    }

    public Courbe<String, Float> getRepartition() {
        return repartition;
    }

    public void setRepartition(Courbe<String, Float> repartition) {
        this.repartition = repartition;
    }
}
