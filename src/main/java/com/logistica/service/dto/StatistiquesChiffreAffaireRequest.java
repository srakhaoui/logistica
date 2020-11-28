package com.logistica.service.dto;

import com.logistica.domain.enumeration.TypeLivraison;

import java.time.LocalDate;

public class StatistiquesChiffreAffaireRequest {

    private Long societeId;
    private Long produitId;
    private String matricule;
    private Long trajetId;
    private TypeLivraison typeLivraison;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private boolean withTotalChiffreAffaire;
    private boolean withEvolutionChiffreAffaire;
    private boolean withRepartitionParTypeLivraison;
    private boolean withRepartitionParSocieteFacturation;
    private boolean withRepartitionParProduit;
    private boolean withRepartitionParTrajet;
    private boolean withRepartitionParMatricule;

    public Long getSocieteId() {
        return societeId;
    }

    public void setSocieteId(Long societeId) {
        this.societeId = societeId;
    }

    public Long getProduitId() {
        return produitId;
    }

    public void setProduitId(Long produitId) {
        this.produitId = produitId;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public Long getTrajetId() {
        return trajetId;
    }

    public void setTrajetId(Long trajetId) {
        this.trajetId = trajetId;
    }

    public TypeLivraison getTypeLivraison() {
        return typeLivraison;
    }

    public void setTypeLivraison(TypeLivraison typeLivraison) {
        this.typeLivraison = typeLivraison;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public boolean isWithTotalChiffreAffaire() {
        return withTotalChiffreAffaire;
    }

    public void setWithTotalChiffreAffaire(boolean withTotalChiffreAffaire) {
        this.withTotalChiffreAffaire = withTotalChiffreAffaire;
    }

    public boolean isWithEvolutionChiffreAffaire() {
        return withEvolutionChiffreAffaire;
    }

    public void setWithEvolutionChiffreAffaire(boolean withEvolutionChiffreAffaire) {
        this.withEvolutionChiffreAffaire = withEvolutionChiffreAffaire;
    }

    public boolean isWithRepartitionParTypeLivraison() {
        return withRepartitionParTypeLivraison;
    }

    public void setWithRepartitionParTypeLivraison(boolean withRepartitionParTypeLivraison) {
        this.withRepartitionParTypeLivraison = withRepartitionParTypeLivraison;
    }

    public boolean isWithRepartitionParSocieteFacturation() {
        return withRepartitionParSocieteFacturation;
    }

    public void setWithRepartitionParSocieteFacturation(boolean withRepartitionParSocieteFacturation) {
        this.withRepartitionParSocieteFacturation = withRepartitionParSocieteFacturation;
    }

    public boolean isWithRepartitionParProduit() {
        return withRepartitionParProduit;
    }

    public void setWithRepartitionParProduit(boolean withRepartitionParProduit) {
        this.withRepartitionParProduit = withRepartitionParProduit;
    }

    public boolean isWithRepartitionParTrajet() {
        return withRepartitionParTrajet;
    }

    public void setWithRepartitionParTrajet(boolean withRepartitionParTrajet) {
        this.withRepartitionParTrajet = withRepartitionParTrajet;
    }

    public boolean isWithRepartitionParMatricule() {
        return withRepartitionParMatricule;
    }

    public void setWithRepartitionParMatricule(boolean withRepartitionParMatricule) {
        this.withRepartitionParMatricule = withRepartitionParMatricule;
    }

    public void defaultAllRepartionTypeTo(boolean enable) {
        withRepartitionParMatricule = enable;
        withRepartitionParProduit = enable;
        withRepartitionParSocieteFacturation = enable;
        withRepartitionParTrajet = enable;
        withRepartitionParTypeLivraison = enable;
    }
}
