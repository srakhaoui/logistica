package com.logistica.service.dto;

import com.logistica.domain.enumeration.ModeReglement;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class ReglementRequest {
    @NotNull
    private long factureId;
    @Positive
    private float montant;
    @NotNull
    private ModeReglement modeReglement;

    private String reference;

    public long getFactureId() {
        return factureId;
    }

    public void setFactureId(long factureId) {
        this.factureId = factureId;
    }

    public float getMontant() {
        return montant;
    }

    public void setMontant(float montant) {
        this.montant = montant;
    }

    public ModeReglement getModeReglement() {
        return modeReglement;
    }

    public void setModeReglement(ModeReglement modeReglement) {
        this.modeReglement = modeReglement;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}
