package com.logistica.service.dto;

import java.time.LocalDate;

public class RecapitulatifGasoilVenteGros {
    private String client;
    private LocalDate dateVente;
    private String carburant;
    private Float quantite;
    private String unite;
    private Float prixVenteUnitaire;
    private Float prixVenteTotal;

    public RecapitulatifGasoilVenteGros(String client, LocalDate dateVente, String carburant, Float quantite, String unite, Float prixVenteUnitaire, Float prixVenteTotal) {
        this.client = client;
        this.dateVente = dateVente;
        this.carburant = carburant;
        this.quantite = quantite;
        this.unite = unite;
        this.prixVenteUnitaire = prixVenteUnitaire;
        this.prixVenteTotal = prixVenteTotal;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public LocalDate getDateVente() {
        return dateVente;
    }

    public void setDateVente(LocalDate dateVente) {
        this.dateVente = dateVente;
    }

    public String getCarburant() {
        return carburant;
    }

    public void setCarburant(String carburant) {
        this.carburant = carburant;
    }

    public Float getQuantite() {
        return quantite;
    }

    public void setQuantite(Float quantite) {
        this.quantite = quantite;
    }

    public String getUnite() {
        return unite;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }

    public Float getPrixVenteUnitaire() {
        return prixVenteUnitaire;
    }

    public void setPrixVenteUnitaire(Float prixVenteUnitaire) {
        this.prixVenteUnitaire = prixVenteUnitaire;
    }

    public Float getPrixVenteTotal() {
        return prixVenteTotal;
    }

    public void setPrixVenteTotal(Float prixVenteTotal) {
        this.prixVenteTotal = prixVenteTotal;
    }
}
