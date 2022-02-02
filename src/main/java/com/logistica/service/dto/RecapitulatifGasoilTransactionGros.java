package com.logistica.service.dto;

import com.logistica.domain.enumeration.UniteGasoilGros;

import java.time.LocalDate;

public class RecapitulatifGasoilTransactionGros implements IRecapitulatifGasoilTransactionGros {

    private String fournisseur;
    private String acheteur;
    private String carburant;
    private LocalDate dateReception;
    private Float quantiteAchetee;
    private UniteGasoilGros uniteAchat;
    private Float prixAchatUnitaire;
    private Float prixAchatTotal;
    private String client;
    private String transporteur;
    private LocalDate dateVente;
    private Float quantiteVendue;
    private UniteGasoilGros uniteVente;
    private Float prixVenteUnitaire;
    private Float prixVenteTotal;
    private Float margeGlobale;
    private Float tauxMarge;
    private String description;

    public RecapitulatifGasoilTransactionGros(String description, String fournisseur, String acheteur, String carburant, LocalDate dateReception, Float quantiteAchetee, UniteGasoilGros uniteAchat, Float prixAchatUnitaire, Float prixAchatTotal, String client, String transporteur, LocalDate dateVente, Float quantiteVendue, UniteGasoilGros uniteVente, Float prixVenteUnitaire, Float prixVenteTotal, Float margeGlobale, Float tauxMarge) {
        this.description = description;
        this.fournisseur = fournisseur;
        this.acheteur = acheteur;
        this.carburant = carburant;
        this.dateReception = dateReception;
        this.quantiteAchetee = quantiteAchetee;
        this.uniteAchat = uniteAchat;
        this.prixAchatUnitaire = prixAchatUnitaire;
        this.prixAchatTotal = prixAchatTotal;
        this.client = client;
        this.transporteur = transporteur;
        this.dateVente = dateVente;
        this.quantiteVendue = quantiteVendue;
        this.uniteVente = uniteVente;
        this.prixVenteUnitaire = prixVenteUnitaire;
        this.prixVenteTotal = prixVenteTotal;
        this.margeGlobale = margeGlobale;
        this.tauxMarge = tauxMarge;
    }

    public String getTransporteur() {
        return transporteur;
    }

    public void setTransporteur(String transporteur) {
        this.transporteur = transporteur;
    }

    public Float getPrixAchatTotal() {
        return prixAchatTotal;
    }

    public void setPrixAchatTotal(Float prixAchatTotal) {
        this.prixAchatTotal = prixAchatTotal;
    }

    public void setFournisseur(String fournisseur) {
        this.fournisseur = fournisseur;
    }

    public void setAcheteur(String acheteur) {
        this.acheteur = acheteur;
    }

    public void setCarburant(String carburant) {
        this.carburant = carburant;
    }

    public void setDateReception(LocalDate dateReception) {
        this.dateReception = dateReception;
    }

    public void setQuantiteAchetee(Float quantiteAchetee) {
        this.quantiteAchetee = quantiteAchetee;
    }

    public void setUniteAchat(UniteGasoilGros uniteAchat) {
        this.uniteAchat = uniteAchat;
    }

    public void setPrixAchatUnitaire(Float prixAchatUnitaire) {
        this.prixAchatUnitaire = prixAchatUnitaire;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public void setDateVente(LocalDate dateVente) {
        this.dateVente = dateVente;
    }

    public void setQuantiteVendue(Float quantiteVendue) {
        this.quantiteVendue = quantiteVendue;
    }

    public void setUniteVente(UniteGasoilGros uniteVente) {
        this.uniteVente = uniteVente;
    }

    public void setPrixVenteUnitaire(Float prixVenteUnitaire) {
        this.prixVenteUnitaire = prixVenteUnitaire;
    }

    public void setPrixVenteTotal(Float prixVenteTotal) {
        this.prixVenteTotal = prixVenteTotal;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toCsv() {
        return null;
    }

    @Override
    public String getFournisseur() {
        return fournisseur;
    }

    @Override
    public String getAcheteur() {
        return acheteur;
    }

    @Override
    public String getCarburant() {
        return carburant;
    }

    @Override
    public LocalDate getDateReception() {
        return dateReception;
    }

    @Override
    public Float getQuantiteAchetee() {
        return quantiteAchetee;
    }

    @Override
    public UniteGasoilGros getUniteAchat() {
        return uniteAchat;
    }

    @Override
    public Float getPrixAchatUnitaire() {
        return prixAchatUnitaire;
    }

    @Override
    public String getClient() {
        return client;
    }

    @Override
    public LocalDate getDateVente() {
        return dateVente;
    }

    @Override
    public Float getQuantiteVendue() {
        return quantiteVendue;
    }

    @Override
    public UniteGasoilGros getUniteVente() {
        return uniteVente;
    }

    @Override
    public Float getPrixVenteUnitaire() {
        return prixVenteUnitaire;
    }

    @Override
    public Float getPrixVenteTotal() {
        return prixVenteTotal;
    }

    public Float getMargeGlobale() {
        return margeGlobale;
    }

    public void setMargeGlobale(Float margeGlobale) {
        this.margeGlobale = margeGlobale;
    }

    public Float getTauxMarge() {
        return tauxMarge;
    }

    public void setTauxMarge(Float tauxMarge) {
        this.tauxMarge = tauxMarge;
    }
}
