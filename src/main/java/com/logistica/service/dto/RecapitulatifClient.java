package com.logistica.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.logistica.domain.enumeration.TypeLivraison;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;

public class RecapitulatifClient implements ICsvConvertible {
    private Long livraisonId;
    private String client;
    private String bonlivraisonMimeType;
    private LocalDate dateBonLivraison;
    private Long numeroBonLivraison;
    private String matricule;
    private String produit;
    private Double totalQuantiteeVendue;
    private Double totalPrixVente;
    private String societeFacturation;
    private TypeLivraison type;

    public RecapitulatifClient(Long livraisonId, String societeFacturation, TypeLivraison typeLivraison, String client, String bonlivraisonMimeType, LocalDate dateBonLivraison, Long numeroBonLivraison, String matricule, String produit, Double totalQuantiteeVendue, Double totalPrixVente) {
        this.livraisonId = livraisonId;
        this.societeFacturation = societeFacturation;
        this.type = typeLivraison;
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

    public Long getNumeroBonLivraison() {
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

    public String getSocieteFacturation() {
        return societeFacturation;
    }

    public void setSocieteFacturation(String societeFacturation) {
        this.societeFacturation = societeFacturation;
    }

    public TypeLivraison getTypeLivraison() {
        return type;
    }

    public void setTypeLivraison(TypeLivraison typeLivraison) {
        this.type = typeLivraison;
    }

    public static String csvHeader() {
        return "client;dateBonLivraison;numeroBonLivraison;matricule;produit;totalQuantiteeVendue;totalPrixVente;societeFacturation;type";
    }

    @Override
    public String toCsv() {
        StringBuilder csv = new StringBuilder();
        csv.append(client).append(";")
            .append(dateBonLivraison).append(";")
            .append(numeroBonLivraison).append(";")
            .append(matricule).append(";")
            .append(produit).append(";")
            .append(totalQuantiteeVendue).append(";")
            .append(totalPrixVente).append(";")
            .append(societeFacturation).append(";")
            .append(type);

        return csv.toString();
    }
}
