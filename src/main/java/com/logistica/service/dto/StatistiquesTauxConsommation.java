package com.logistica.service.dto;

public class StatistiquesTauxConsommation {
    private Courbe<String, Float> evolutionLitrage;
    private Courbe<String, Float> evolutionKilometrage;
    private Courbe<String, Float> evolutionTauxConsommation;
    private Float litrageTotal;
    private Float kilometrageTotal;
    private Float tauxConsommation;
    private Courbe<String, Float> tauxConsommationParMatricule;

    public Courbe<String, Float> getEvolutionLitrage() {
        return evolutionLitrage;
    }

    public StatistiquesTauxConsommation withEvolutionLitrage(Courbe<String, Float> evolutionLitrage) {
        this.evolutionLitrage = evolutionLitrage;
        return this;
    }

    public Courbe<String, Float> getEvolutionKilometrage() {
        return evolutionKilometrage;
    }

    public StatistiquesTauxConsommation withEvolutionKilometrage(Courbe<String, Float> evolutionKilometrage) {
        this.evolutionKilometrage = evolutionKilometrage;
        return this;
    }

    public Courbe<String, Float> getEvolutionTauxConsommation() {
        return evolutionTauxConsommation;
    }

    public StatistiquesTauxConsommation withEvolutionTauxConsommation(Courbe<String, Float> evolutionTauxConsommation) {
        this.evolutionTauxConsommation = evolutionTauxConsommation;
        return this;
    }

    public Float getLitrageTotal() {
        return litrageTotal;
    }

    public StatistiquesTauxConsommation withLitrageTotal(Float litrageTotal) {
        this.litrageTotal = litrageTotal;
        return this;
    }

    public Float getKilometrageTotal() {
        return kilometrageTotal;
    }

    public StatistiquesTauxConsommation withKilometrageTotal(Float kilometrageTotal) {
        this.kilometrageTotal = kilometrageTotal;
        return this;
    }

    public Float getTauxConsommation() {
        return tauxConsommation;
    }

    public StatistiquesTauxConsommation withTauxConsommation(Float tauxConsommation) {
        this.tauxConsommation = tauxConsommation;
        return this;
    }

    public Courbe<String, Float> getTauxConsommationParMatricule() {
        return tauxConsommationParMatricule;
    }

    public StatistiquesTauxConsommation withTauxConsommationParMatricule(Courbe<String, Float> tauxConsommationParMatricule) {
        this.tauxConsommationParMatricule = tauxConsommationParMatricule;
        return this;
    }
}
