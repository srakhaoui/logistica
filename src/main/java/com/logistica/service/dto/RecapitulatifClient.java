package com.logistica.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;

public class RecapitulatifClient {
    private Long livraisonId;
    private String client;
    private String bonlivraisonMimeType;
    private LocalDate dateBonLivraison;
    private Integer numeroBonLivraison;
    private String matricule;
    private String produit;
    private Double totalQuantiteeVendue;
    private Double totalPrixVente;

    public RecapitulatifClient(Long livraisonId, String client, String bonlivraisonMimeType, LocalDate dateBonLivraison, Integer numeroBonLivraison, String matricule, String produit, Double totalQuantiteeVendue, Double totalPrixVente) {
        this.livraisonId = livraisonId;
        this.client = client;
        this.bonlivraisonMimeType = bonlivraisonMimeType;
        this.dateBonLivraison = dateBonLivraison;
        this.numeroBonLivraison = numeroBonLivraison;
        this.matricule = matricule;
        this.produit = produit;
        this.totalQuantiteeVendue = totalQuantiteeVendue;
        this.totalPrixVente = totalPrixVente;
    }

    public Long getLivraisonId() {
        return livraisonId;
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

    @JsonProperty("hasBonLivraison")
    public Boolean hasBonLivraison() {
        return StringUtils.isNotBlank(bonlivraisonMimeType);
    }
}
