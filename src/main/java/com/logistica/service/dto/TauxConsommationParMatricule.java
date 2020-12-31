package com.logistica.service.dto;

public class TauxConsommationParMatricule implements Comparable<TauxConsommationParMatricule> {
    private Double tauxConsommation;
    private String matricule;

    public TauxConsommationParMatricule(String matricule, Double tauxConsommation) {
        this.matricule = matricule;
        this.tauxConsommation = tauxConsommation;
    }

    public Double getTauxConsommation() {
        return tauxConsommation;
    }

    public void setTauxConsommation(Double tauxConsommation) {
        this.tauxConsommation = tauxConsommation;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    @Override
    public int compareTo(TauxConsommationParMatricule tauxConsommationParMatricule) {
        return tauxConsommationParMatricule.tauxConsommation.compareTo(tauxConsommation);
    }
}
