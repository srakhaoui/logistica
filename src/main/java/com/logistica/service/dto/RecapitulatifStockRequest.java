package com.logistica.service.dto;

public class RecapitulatifStockRequest {

    private String depot;

    public RecapitulatifStockRequest() {
    }

    public RecapitulatifStockRequest(String depot) {
        this.depot = depot;
    }

    public String getDepot() {
        return depot;
    }

    public void setDepot(String depot) {
        this.depot = depot;
    }
}
