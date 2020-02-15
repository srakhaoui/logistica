package com.logistica.service.dto;

import com.logistica.domain.enumeration.Unite;

public class RecapitulatifFacturation {

    private Integer moisBonLivraison;
    private String produit;
    private Unite uniteVente;
    private Double totalQuantiteeVendue;
    private Double totalPrixVente;

    public RecapitulatifFacturation(Integer moisBonLivraison, String produit, Unite uniteVente, Double totalQuantiteeVendue, Double totalPrixVente){
        this.moisBonLivraison =moisBonLivraison;
        this.produit = produit;
        this.uniteVente = uniteVente;
        this.totalQuantiteeVendue = totalQuantiteeVendue;
        this.totalPrixVente = totalPrixVente;
    }

    public Integer getMoisBonLivraison() {
        return moisBonLivraison;
    }

    public String getProduit() {
        return produit;
    }

    public Unite getUniteVente() {
        return uniteVente;
    }

    public Double getTotalQuantiteeVendue() {
        return totalQuantiteeVendue;
    }

    public Double getTotalPrixVente() {
        return totalPrixVente;
    }
}
