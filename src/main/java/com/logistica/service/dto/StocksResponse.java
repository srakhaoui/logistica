package com.logistica.service.dto;

public class StocksResponse {
    private String depot;
    private boolean depotReserve;
    private float stockInitial;
    private float entreesAchat;
    private float entreesTransfet;
    private float sorties;
    private float consommationInterne;
    private float stock;

    public StocksResponse(String depot, boolean depotReserve, float stockInitial, double entreesAchat, double entreesTransfet, double sorties, double consommationInterne) {
        this(depot, depotReserve, stockInitial, entreesAchat, entreesTransfet, sorties);
        this.consommationInterne = (float) consommationInterne;
        this.stock = (float) (stock - consommationInterne);
    }

    public StocksResponse(String depot, boolean depotReserve, float stockInitial, double entreesAchat, double entreesTransfet, double sorties) {
        this.depot = depot;
        this.depotReserve = depotReserve;
        this.stockInitial = stockInitial;
        this.entreesAchat = (float) entreesAchat;
        this.entreesTransfet = (float) entreesTransfet;
        this.sorties = (float) sorties;
        this.stock = (float) (stockInitial + entreesAchat + entreesTransfet - sorties);
    }

    public String getDepot() {
        return depot;
    }

    public void setDepot(String depot) {
        this.depot = depot;
    }

    public float getStockInitial() {
        return stockInitial;
    }

    public void setStockInitial(float stockInitial) {
        this.stockInitial = stockInitial;
    }

    public boolean isDepotReserve() {
        return depotReserve;
    }

    public void setDepotReserve(boolean depotReserve) {
        this.depotReserve = depotReserve;
    }

    public double getEntreesAchat() {
        return entreesAchat;
    }

    public void setEntreesAchat(float entreesAchat) {
        this.entreesAchat = entreesAchat;
    }

    public float getEntreesTransfet() {
        return entreesTransfet;
    }

    public void setEntreesTransfet(float entreesTransfet) {
        this.entreesTransfet = entreesTransfet;
    }

    public double getSorties() {
        return sorties;
    }

    public void setSorties(float sorties) {
        this.sorties = sorties;
    }

    public double getConsommationInterne() {
        return consommationInterne;
    }

    public void setConsommationInterne(float consommationInterne) {
        this.consommationInterne = consommationInterne;
    }

    public double getStock() {
        return stock;
    }
}
