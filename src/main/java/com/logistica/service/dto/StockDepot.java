package com.logistica.service.dto;

import com.logistica.domain.enumeration.Unite;

public class StockDepot {

    private final String depot;
    private final Unite unite;
    private final Float total;

    public StockDepot(String depot, Unite unite, Double total) {
        this.depot = depot;
        this.unite = unite;
        this.total = total.floatValue();
    }

    public StockDepot(String depot, Unite unite, Float total) {
        this.depot = depot;
        this.unite = unite;
        this.total = total.floatValue();
    }

    public String getDepot() {
        return depot;
    }

    public Unite getUnite() {
        return unite;
    }

    public Float getTotal() {
        return total;
    }
}
