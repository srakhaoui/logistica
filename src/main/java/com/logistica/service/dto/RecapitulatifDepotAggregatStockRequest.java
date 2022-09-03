package com.logistica.service.dto;

import java.time.LocalDate;

public class RecapitulatifDepotAggregatStockRequest {
    private LocalDate dateDebut;

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }
}
