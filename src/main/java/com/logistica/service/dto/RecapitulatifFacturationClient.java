package com.logistica.service.dto;

public class RecapitulatifFacturationClient {

    private String codeProduit;

    private String categorieProduit;
    private Long nombreBonsLivraison;
    private Double totalQuantiteeVendue;
    private Double totalPrixVente;

    public RecapitulatifFacturationClient(String codeProduit, String categorieProduit, Long nombreBonsLivraison, Double totalQuantiteeVendue, Double totalPrixVente) {
        this.codeProduit = codeProduit;
        this.categorieProduit = categorieProduit;
        this.nombreBonsLivraison = nombreBonsLivraison;
        this.totalQuantiteeVendue = totalQuantiteeVendue;
        this.totalPrixVente = totalPrixVente;
    }

    public String getCodeProduit() {
        return codeProduit;
    }

    public String getCategorieProduit() {
        return categorieProduit;
    }

    public Long getNombreBonsLivraison() {
        return nombreBonsLivraison;
    }

    public Double getTotalQuantiteeVendue() {
        return totalQuantiteeVendue;
    }

    public Double getTotalPrixVente() {
        return totalPrixVente;
    }

    public void setTotalPrixVente(Double totalPrixVente) {
        this.totalPrixVente = totalPrixVente;
    }

}
