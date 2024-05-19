package com.logistica.service.dto;

import com.logistica.domain.Reglement;

public class ReglementResponse {
    private long id;
    private boolean created;
    private double montant;

    private void setCreated(boolean created) {
        this.created = created;
    }

    private void setId(Long id) {
        this.id = id;
    }

    private void setMontant(double montant) {
        this.montant = montant;
    }

    public long getId() {
        return id;
    }

    public boolean isCreated() {
        return created;
    }

    public double getMontant() {
        return montant;
    }

    public static ReglementResponse of(Reglement reglement) {
        ReglementResponse reglementResponse = new ReglementResponse();
        reglementResponse.setMontant(reglement.getMontant());
        reglementResponse.setId(reglement.getId());
        reglementResponse.setCreated(true);
        return reglementResponse;
    }

    public static ReglementResponse noPayment() {
        ReglementResponse reglementResponse = new ReglementResponse();
        reglementResponse.setCreated(false);
        return reglementResponse;
    }
}
