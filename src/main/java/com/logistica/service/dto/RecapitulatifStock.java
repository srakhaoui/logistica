package com.logistica.service.dto;

public class RecapitulatifStock {
    private String depot;
    private boolean depotReserve;
    private float stockInitial;
    private float entreesAchat;
    private float entreesTransfert;
    private float sorties;
    private float sortiesTransfert;
    private float consommationInterne;
    private float stock;

    public RecapitulatifStock() {
    }

    public RecapitulatifStock calculerStock() {
        this.stock = stockInitial + entreesAchat + entreesTransfert - sorties - sortiesTransfert - consommationInterne;
        return this;
    }

    public RecapitulatifStock stock(float stock) {
        this.stock = stock;
        return this;
    }

    public RecapitulatifStock consommationInterne(float consommationInterne) {
        this.consommationInterne = consommationInterne;
        return this;
    }

    public RecapitulatifStock sorties(float sorties) {
        this.sorties = sorties;
        return this;
    }

    public RecapitulatifStock sortiesTransfert(float sortiesTransfert) {
        this.sortiesTransfert = sortiesTransfert;
        return this;
    }

    public RecapitulatifStock entreesTransfert(float entreesTransfert) {
        this.entreesTransfert = entreesTransfert;
        return this;
    }

    public RecapitulatifStock entreesAchat(float entreesAchat) {
        this.entreesAchat = entreesAchat;
        return this;
    }

    public RecapitulatifStock stockInitial(float stockInitial) {
        this.stockInitial = stockInitial;
        return this;
    }

    public RecapitulatifStock depotReserve(boolean depotReserve) {
        this.depotReserve = depotReserve;
        return this;
    }

    public RecapitulatifStock depot(String depot) {
        this.depot = depot;
        return this;
    }

    public String getDepot() {
        return depot;
    }

    public float getStockInitial() {
        return stockInitial;
    }

    public boolean isDepotReserve() {
        return depotReserve;
    }

    public double getEntreesAchat() {
        return entreesAchat;
    }

    public float getEntreesTransfert() {
        return entreesTransfert;
    }

    public double getSorties() {
        return sorties;
    }

    public float getSortiesTransfert() {
        return sortiesTransfert;
    }

    public double getConsommationInterne() {
        return consommationInterne;
    }

    public double getStock() {
        return stock;
    }
}
