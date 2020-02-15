package com.logistica.service.dto;

import java.time.LocalDate;

public class RecapitulatifClient {
    private String client;
    private LocalDate dateBonLivraison;
    private Integer numeroBonLivraison;
    private String matricule;
    private String produit;
    private Double totalQuantiteeVendue;
    private Double totalPrixVente;

    public RecapitulatifClient(String client, LocalDate dateBonLivraison, Integer numeroBonLivraison, String matricule, String produit, Double totalQuantiteeVendue, Double totalPrixVente){
        this.client = client;
        this.dateBonLivraison = dateBonLivraison;
        this.numeroBonLivraison = numeroBonLivraison;
        this.matricule = matricule;
        this.produit = produit;
        this.totalQuantiteeVendue = totalQuantiteeVendue;
        this.totalPrixVente = totalPrixVente;
    }

    public String getClient() {
        return client;
    }

    public LocalDate getDateBonLivraison() {
        return dateBonLivraison;
    }

    public Integer getNumeroBonLivraison() {
        return numeroBonLivraison;
    }

    public String getMatricule() {
        return matricule;
    }

    public String getProduit() {
        return produit;
    }

    public Double getTotalQuantiteeVendue() {
        return totalQuantiteeVendue;
    }

    public Double getTotalPrixVente() {
        return totalPrixVente;
    }
}
