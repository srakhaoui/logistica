package com.logistica.service.dto;

import com.logistica.service.util.MathUtil;

import java.util.List;
import java.util.Optional;

public class RecapitulatifGasoilGros {

    private List<RecapitulatifGasoilTransactionGros> recapitulatifGasoilTransactionGrosList;
    private Float totalQuantiteAchetee;
    private Float totalPrixAchat;
    private Float totalQuantiteVendue;
    private Float totalPrixVente;
    private float margeGlobale;
    private float tauxMarge;

    public RecapitulatifGasoilGros(Double totalQuantiteAchetee, Double totalPrixAchat, Double totalQuantiteVendue, Double totalPrixVente) {
        this.totalQuantiteAchetee = totalQuantiteAchetee.floatValue();
        this.totalPrixAchat = totalPrixAchat.floatValue();
        this.totalQuantiteVendue = totalQuantiteVendue.floatValue();
        this.totalPrixVente = totalPrixVente.floatValue();
        this.margeGlobale = totalPrixVente.floatValue() - totalPrixAchat.floatValue();
        this.tauxMarge = MathUtil.roundUp(100 * Optional.ofNullable(margeGlobale).orElse(0.0F) / Optional.ofNullable(totalPrixAchat.floatValue()).orElse(0.0F));
    }

    public List<RecapitulatifGasoilTransactionGros> getRecapitulatifGasoilTransactionGrosList() {
        return recapitulatifGasoilTransactionGrosList;
    }

    public void setRecapitulatifGasoilTransactionGrosList(List<RecapitulatifGasoilTransactionGros> recapitulatifGasoilTransactionGrosList) {
        this.recapitulatifGasoilTransactionGrosList = recapitulatifGasoilTransactionGrosList;
    }

    public Float getTotalQuantiteAchetee() {
        return totalQuantiteAchetee;
    }

    public void setTotalQuantiteAchetee(Float totalQuantiteAchetee) {
        this.totalQuantiteAchetee = totalQuantiteAchetee;
    }

    public Float getTotalPrixAchat() {
        return totalPrixAchat;
    }

    public void setTotalPrixAchat(Float totalPrixAchat) {
        this.totalPrixAchat = totalPrixAchat;
    }

    public Float getTotalQuantiteVendue() {
        return totalQuantiteVendue;
    }

    public void setTotalQuantiteVendue(Float totalQuantiteVendue) {
        this.totalQuantiteVendue = totalQuantiteVendue;
    }

    public Float getTotalPrixVente() {
        return totalPrixVente;
    }

    public void setTotalPrixVente(Float totalPrixVente) {
        this.totalPrixVente = totalPrixVente;
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
