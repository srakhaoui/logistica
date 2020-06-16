package com.logistica.service.dto;

import com.logistica.domain.enumeration.Unite;

public class RecapitulatifFacturation implements ICsvConvertible {

    private Integer moisBonLivraison;
    private String produit;
    private Unite uniteVente;
    private Double totalQuantiteeVendue;
    private Double totalPrixVente;

    public RecapitulatifFacturation(Integer moisBonLivraison, String produit, Unite uniteVente, Double totalQuantiteeVendue, Double totalPrixVente) {
        this.moisBonLivraison = moisBonLivraison;
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

    public static String csvHeader() {
        return "moisBonLivraison;produit;uniteVente;totalQuantiteeVendue;totalPrixVente";
    }

    @Override
    public String toCsv() {
        StringBuilder csv = new StringBuilder();
        csv.append(moisBonLivraison).append(";")
            .append(produit).append(";")
            .append(uniteVente).append(";")
            .append(totalQuantiteeVendue).append(";")
            .append(totalPrixVente);
        return csv.toString();
    }
}
