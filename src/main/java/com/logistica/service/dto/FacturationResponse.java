package com.logistica.service.dto;

import com.logistica.domain.Facture;

public class FacturationResponse {
    private long nombreBonsLivraison;
    private double prixTtc;
    private String nomClient;
    private Long id;

    public static FacturationResponse from(Facture facture) {
        FacturationResponse facturationResponse = new FacturationResponse();
        facturationResponse.setId(facture.getId());
        facturationResponse.setNombreBonsLivraison(facture.getNombreBonsLivraison());
        facturationResponse.setPrixTtc(facture.getTotalPrixHt());
        facturationResponse.setNomClient(facture.getClient().getNom());
        return facturationResponse;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public static FacturationResponse noInvoice() {
        return new FacturationResponse();
    }

    public Long getNombreBonsLivraison() {
        return nombreBonsLivraison;
    }

    public void setNombreBonsLivraison(long nombreBonsLivraison) {
        this.nombreBonsLivraison = nombreBonsLivraison;
    }

    public double getPrixTtc() {
        return prixTtc;
    }

    public void setPrixTtc(double prixTtc) {
        this.prixTtc = prixTtc;
    }

    public String getNomClient() {
        return nomClient;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }
}
