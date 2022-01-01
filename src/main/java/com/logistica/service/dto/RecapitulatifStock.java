package com.logistica.service.dto;

public class RecapitulatifStock {
    private String depot;
    private boolean depotReserve;
    private float stockInitial;
    private float entreesAchat;
    private float entreesTransfert;
    private float sorties;
    private float consommationInterne;
    private float stock;

    public RecapitulatifStock(String depot, boolean depotReserve, float stockInitial, double entreesAchat, double entreesTransfert, double sorties, double consommationInterne) {
        this(depot, depotReserve, stockInitial, entreesAchat, entreesTransfert, sorties);
        this.consommationInterne = (float) consommationInterne;
        this.stock = (float) (stock - consommationInterne);
    }

    public RecapitulatifStock(String depot, boolean depotReserve, float stockInitial, double entreesAchat, double entreesTransfert, double sorties) {
        this.depot = depot;
        this.depotReserve = depotReserve;
        this.stockInitial = stockInitial;
        this.entreesAchat = (float) entreesAchat;
        this.entreesTransfert = (float) entreesTransfert;
        this.sorties = (float) sorties;
        this.stock = (float) (stockInitial + entreesAchat + entreesTransfert - sorties);
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

    public float getEntreesTransfert() {
        return entreesTransfert;
    }

    public void setEntreesTransfert(float entreesTransfert) {
        this.entreesTransfert = entreesTransfert;
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
