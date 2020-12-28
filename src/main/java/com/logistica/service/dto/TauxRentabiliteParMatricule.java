package com.logistica.service.dto;

public class TauxRentabiliteParMatricule implements Comparable<TauxRentabiliteParMatricule> {
    private Float tauxRentabilite;
    private String matricule;

    public TauxRentabiliteParMatricule(String matricule, Float tauxRentabilite) {
        this.matricule = matricule;
        this.tauxRentabilite = tauxRentabilite;
    }

    public Float getTauxRentabilite() {
        return tauxRentabilite;
    }

    public String getMatricule() {
        return matricule;
    }

    @Override
    public int compareTo(TauxRentabiliteParMatricule tauxRentabiliteParMatricule) {
        return tauxRentabiliteParMatricule.tauxRentabilite.compareTo(this.tauxRentabilite);
    }
}
