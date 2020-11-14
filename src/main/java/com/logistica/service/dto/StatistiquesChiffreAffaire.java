package com.logistica.service.dto;

public class StatistiquesChiffreAffaire {

    private Float totalChiffreAffaire;
    private Courbe<String, Float> evolution;
    private Courbe<String, Float> chiffreAffaireParTrajet;
    private Courbe<String, Float> chiffreAffaireParProduit;
    private Courbe<String, Float> chiffreAffaireParSociete;
    private Courbe<String, Float> chiffreAffaireParMatricule;
    private Courbe<String, Float> chiffreAffaireParType;

    public Float getTotalChiffreAffaire() {
        return totalChiffreAffaire;
    }

    public StatistiquesChiffreAffaire totalChiffreAffaire(Float totalChiffreAffaire) {
        this.totalChiffreAffaire = totalChiffreAffaire;
        return this;
    }

    public Courbe<String, Float> getEvolution() {
        return evolution;
    }

    public StatistiquesChiffreAffaire evolution(Courbe<String, Float> evolution) {
        this.evolution = evolution;
        return this;
    }

    public Courbe<String, Float> getChiffreAffaireParTrajet() {
        return chiffreAffaireParTrajet;
    }

    public StatistiquesChiffreAffaire trajet(Courbe<String, Float> chiffreAffaireParTrajet) {
        this.chiffreAffaireParTrajet = chiffreAffaireParTrajet;
        return this;
    }

    public Courbe<String, Float> getChiffreAffaireParProduit() {
        return chiffreAffaireParProduit;
    }

    public StatistiquesChiffreAffaire produit(Courbe<String, Float> chiffreAffaireParProduit) {
        this.chiffreAffaireParProduit = chiffreAffaireParProduit;
        return this;
    }

    public Courbe<String, Float> getChiffreAffaireParSociete() {
        return chiffreAffaireParSociete;
    }

    public StatistiquesChiffreAffaire societeFacturation(Courbe<String, Float> chiffreAffaireParSociete) {
        this.chiffreAffaireParSociete = chiffreAffaireParSociete;
        return this;
    }

    public Courbe<String, Float> getChiffreAffaireParMatricule() {
        return chiffreAffaireParMatricule;
    }

    public StatistiquesChiffreAffaire matricule(Courbe<String, Float> chiffreAffaireParMatricule) {
        this.chiffreAffaireParMatricule = chiffreAffaireParMatricule;
        return this;
    }

    public Courbe<String, Float> getChiffreAffaireParType() {
        return chiffreAffaireParType;
    }

    public StatistiquesChiffreAffaire typeLivraison(Courbe<String, Float> chiffreAffaireParType) {
        this.chiffreAffaireParType = chiffreAffaireParType;
        return this;
    }
}
