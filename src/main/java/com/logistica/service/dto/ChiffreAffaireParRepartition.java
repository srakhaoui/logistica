package com.logistica.service.dto;

public class ChiffreAffaireParRepartition {
    private String elementRepartition;
    private Double chiffreAffaire;

    public ChiffreAffaireParRepartition(String elementRepartition, Double chiffreAffaire) {
        this.elementRepartition = elementRepartition;
        this.chiffreAffaire = chiffreAffaire;
    }

    public String getElementRepartition() {
        return elementRepartition;
    }

    public void setElementRepartition(String elementRepartition) {
        this.elementRepartition = elementRepartition;
    }

    public Double getChiffreAffaire() {
        return chiffreAffaire;
    }

    public void setChiffreAffaire(Double chiffreAffaire) {
        this.chiffreAffaire = chiffreAffaire;
    }
}
