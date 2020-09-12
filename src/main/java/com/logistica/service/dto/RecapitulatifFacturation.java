package com.logistica.service.dto;

import com.logistica.domain.enumeration.Unite;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

public class RecapitulatifFacturation implements ICsvConvertible {

    private Integer moisBonLivraison;
    private String produit;
    private Unite uniteVente;
    private Double totalQuantiteeVendue;
    private Double totalPrixVente;
    private boolean facture;
    private String societe;
    private String client;

    public RecapitulatifFacturation(Integer moisBonLivraison, String produit, Unite uniteVente, Double totalQuantiteeVendue, Double totalPrixVente) {
        this.moisBonLivraison = moisBonLivraison;
        this.produit = produit;
        this.uniteVente = uniteVente;
        this.totalQuantiteeVendue = totalQuantiteeVendue;
        this.totalPrixVente = totalPrixVente;
    }

    public RecapitulatifFacturation(Integer moisBonLivraison, String produit, Unite uniteVente, boolean facture, Double totalQuantiteeVendue, Double totalPrixVente) {
        this(moisBonLivraison, produit, uniteVente, totalQuantiteeVendue, totalPrixVente);
        this.facture = facture;
    }

    public RecapitulatifFacturation(Integer moisBonLivraison, String societe, String produit, Unite uniteVente, String client, boolean facture, Double totalQuantiteeVendue, Double totalPrixVente) {
        this(moisBonLivraison, produit, uniteVente, facture, totalQuantiteeVendue, totalPrixVente);
        this.societe = societe;
        this.client = client;
    }

    public RecapitulatifFacturation(Integer moisBonLivraison, String societe, String produit, Unite uniteVente, String client, Double totalQuantiteeVendue, Double totalPrixVente) {
        this(moisBonLivraison, produit, uniteVente, totalQuantiteeVendue, totalPrixVente);
        this.societe = societe;
        this.client = client;
    }

    public RecapitulatifFacturation(Integer moisBonLivraison, String societe, String produit, Unite uniteVente, boolean facture, Double totalQuantiteeVendue, Double totalPrixVente) {
        this(moisBonLivraison, produit, uniteVente, facture, totalQuantiteeVendue, totalPrixVente);
        this.societe = societe;
    }

    public RecapitulatifFacturation(Integer moisBonLivraison, String societe, String produit, Unite uniteVente, Double totalQuantiteeVendue, Double totalPrixVente) {
        this(moisBonLivraison, produit, uniteVente, totalQuantiteeVendue, totalPrixVente);
        this.societe = societe;
    }

    public RecapitulatifFacturation(Integer moisBonLivraison, String produit, Unite uniteVente, String client, Double totalQuantiteeVendue, Double totalPrixVente) {
        this(moisBonLivraison, produit, uniteVente, totalQuantiteeVendue, totalPrixVente);
        this.client = client;
    }

    public RecapitulatifFacturation(Integer moisBonLivraison, String produit, Unite uniteVente, String client, boolean facture, Double totalQuantiteeVendue, Double totalPrixVente) {
        this(moisBonLivraison, produit, uniteVente, facture, totalQuantiteeVendue, totalPrixVente);
        this.client = client;
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

    public boolean isFacture() {
        return facture;
    }

    public void setFacture(boolean facture) {
        this.facture = facture;
    }

    public String getSociete() {
        return societe;
    }

    public void setSociete(String societe) {
        this.societe = societe;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public static String csvHeader() {
        return "moisBonLivraison;societeFacturation;produit;uniteVente;client;facture;totalQuantiteeVendue;totalPrixVente";
    }

    @Override
    public String toCsv() {
        try {
            StringBuilder csv = new StringBuilder();
            csv.append(moisBonLivraison).append(";")
                .append(Optional.ofNullable(societe).orElse("-")).append(";")
                .append(Optional.ofNullable(produit).orElse("-")).append(";")
                .append(uniteVente).append(";")
                .append(Optional.ofNullable(client).orElse("-")).append(";")
                .append(facture).append(";")
                .append(StringUtils.replaceChars(Double.toString(Optional.ofNullable(totalQuantiteeVendue).orElse(0.0)), '.', ',')).append(";")
                .append(StringUtils.replaceChars(Double.toString(Optional.ofNullable(totalPrixVente).orElse(0.0)), '.', ','));
            return csv.toString();
        } catch (Exception ex) {
            return "error;error;error;error;error;error;error;error";
        }
    }
}
