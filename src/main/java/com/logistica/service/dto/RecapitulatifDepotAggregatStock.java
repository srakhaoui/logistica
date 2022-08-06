package com.logistica.service.dto;

import com.logistica.domain.enumeration.Unite;

public class RecapitulatifDepotAggregatStock {
    private final String nom;
    private final Float initialStock;
    private Unite uniteIntialStock;
    private float entreesAchat;
    private Unite uniteEntreesAchat;
    private float entreesTransfert;
    private Unite uniteTransfertAchat;
    private float sortiesVente;
    private Unite uniteSortiesAchat;
    private float sortiesTransfert;
    private Unite uniteSortiesTransfert;
    private Float stock;

    public RecapitulatifDepotAggregatStock(String nom, Float initialStock) {
        this.nom = nom;
        this.initialStock = initialStock;
    }

    public String getNom() {
        return nom;
    }

    public RecapitulatifDepotAggregatStock entreesAchat(float entreesAchat) {
        this.entreesAchat = entreesAchat;
        return this;
    }

    public RecapitulatifDepotAggregatStock entreesTransfert(float entreesTransfert) {
        this.entreesTransfert = entreesTransfert;
        return this;
    }

    public RecapitulatifDepotAggregatStock sortiesVente(float sortiesVente) {
        this.sortiesVente = sortiesVente;
        return this;
    }

    public RecapitulatifDepotAggregatStock sortiesTransfert(float sortiesTransfert) {
        this.sortiesTransfert = sortiesTransfert;
        return this;
    }

    public RecapitulatifDepotAggregatStock uniteIntialStock(Unite uniteIntialStock) {
        this.uniteIntialStock = uniteIntialStock;
        return this;
    }

    public RecapitulatifDepotAggregatStock uniteEntreesAchat(Unite uniteEntreesAchat) {
        this.uniteEntreesAchat = uniteEntreesAchat;
        return this;
    }

    public RecapitulatifDepotAggregatStock uniteTransfertAchat(Unite uniteTransfertAchat) {
        this.uniteTransfertAchat = uniteTransfertAchat;
        return this;
    }

    public RecapitulatifDepotAggregatStock uniteSortiesAchat(Unite uniteSortiesAchat) {
        this.uniteSortiesAchat = uniteSortiesAchat;
        return this;
    }

    public RecapitulatifDepotAggregatStock uniteSortiesTransfert(Unite uniteSortiesTransfert) {
        this.uniteSortiesTransfert = uniteSortiesTransfert;
        return this;
    }

    public RecapitulatifDepotAggregatStock calculerStock() {
        this.stock = initialStock + entreesAchat + entreesTransfert - sortiesVente - sortiesTransfert;
        return this;
    }

    public Float getInitialStock() {
        return initialStock;
    }

    public float getEntreesAchat() {
        return entreesAchat;
    }

    public float getEntreesTransfert() {
        return entreesTransfert;
    }

    public float getSortiesVente() {
        return sortiesVente;
    }

    public float getSortiesTransfert() {
        return sortiesTransfert;
    }

    public Unite getUniteIntialStock() {
        return uniteIntialStock;
    }

    public Unite getUniteEntreesAchat() {
        return uniteEntreesAchat;
    }

    public Unite getUniteTransfertAchat() {
        return uniteTransfertAchat;
    }

    public Unite getUniteSortiesAchat() {
        return uniteSortiesAchat;
    }

    public Unite getUniteSortiesTransfert() {
        return uniteSortiesTransfert;
    }

    public Float getStock() {
        return stock;
    }
}
