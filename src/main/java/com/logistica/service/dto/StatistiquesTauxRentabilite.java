package com.logistica.service.dto;

public class StatistiquesTauxRentabilite {
    private Courbe<String, Float> evolutionChiffreAffaire;
    private Courbe<String, Float> evolutionChargeGasoil;
    private Courbe<String, Float> evolutionTauxRentabilite;
    private Float chiffreAffaireTotal;
    private Float chargeGasoilTotal;
    private Float tauxRentabilite;
    private Courbe<String, Float> tauxRentabiliteParMatricule;

    public Courbe<String, Float> getEvolutionChiffreAffaire() {
        return evolutionChiffreAffaire;
    }

    public StatistiquesTauxRentabilite evolutionChiffreAffaire(Courbe<String, Float> evolutionChiffreAffaire) {
        this.evolutionChiffreAffaire = evolutionChiffreAffaire;
        return this;
    }

    public Courbe<String, Float> getEvolutionChargeGasoil() {
        return evolutionChargeGasoil;
    }

    public StatistiquesTauxRentabilite evolutionChargeGasoil(Courbe<String, Float> evolutionChargeGasoil) {
        this.evolutionChargeGasoil = evolutionChargeGasoil;
        return this;
    }

    public Courbe<String, Float> getEvolutionTauxRentabilite() {
        return evolutionTauxRentabilite;
    }

    public StatistiquesTauxRentabilite evolutionTauxRentabilite(Courbe<String, Float> evolutionTauxRentabilite) {
        this.evolutionTauxRentabilite = evolutionTauxRentabilite;
        return this;
    }

    public Float getChiffreAffaireTotal() {
        return chiffreAffaireTotal;
    }

    public StatistiquesTauxRentabilite chiffreAffaireTotal(Float chiffreAffaireTotal) {
        this.chiffreAffaireTotal = chiffreAffaireTotal;
        return this;
    }

    public Float getChargeGasoilTotal() {
        return chargeGasoilTotal;
    }

    public StatistiquesTauxRentabilite chargeGasoilTotal(Float chargeGasoilTotal) {
        this.chargeGasoilTotal = chargeGasoilTotal;
        return this;
    }

    public Float getTauxRentabilite() {
        return tauxRentabilite;
    }

    public StatistiquesTauxRentabilite tauxRentabilite(Float tauxRentabilite) {
        this.tauxRentabilite = tauxRentabilite;
        return this;
    }

    public Courbe<String, Float> getTauxRentabiliteParMatricule() {
        return tauxRentabiliteParMatricule;
    }

    public StatistiquesTauxRentabilite tauxRentabiliteParMatricule(Courbe<String, Float> tauxRentabiliteParMatricule) {
        this.tauxRentabiliteParMatricule = tauxRentabiliteParMatricule;
        return this;
    }
}
