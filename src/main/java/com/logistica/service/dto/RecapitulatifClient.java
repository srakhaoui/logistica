package com.logistica.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.logistica.domain.enumeration.TypeLivraison;
import com.logistica.domain.enumeration.Unite;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.util.Optional;

public class RecapitulatifClient implements ICsvConvertible {
    private String client;
    private String bonlivraisonMimeType;
    private LocalDate dateBonLivraison;
    private boolean facture;
    private String numeroBonLivraison;
    private String matricule;
    private String produit;
    private Double totalQuantiteeVendue;
    private Unite uniteVente;
    private Double totalPrixVente;
    private String societeFacturation;
    private TypeLivraison type;
    private String fournisseur;
    private Float quantiteAchetee;
    private Unite uniteAchat;
    private Float prixTotalAchat;
    private String chantier;


    public RecapitulatifClient(String societeFacturation, TypeLivraison typeLivraison, String client, String bonlivraisonMimeType, LocalDate dateBonLivraison, String numeroBonLivraison, String matricule, String produit, Double totalQuantiteeVendue, Unite uniteVente, Double totalPrixVente, boolean facture, String chantier) {
        this.societeFacturation = societeFacturation;
        this.type = typeLivraison;
        this.client = client;
        this.bonlivraisonMimeType = bonlivraisonMimeType;
        this.dateBonLivraison = dateBonLivraison;
        this.numeroBonLivraison = numeroBonLivraison;
        this.matricule = matricule;
        this.produit = produit;
        this.totalQuantiteeVendue = totalQuantiteeVendue;
        this.uniteVente = uniteVente;
        this.totalPrixVente = totalPrixVente;
        this.facture = facture;
        this.chantier = chantier;
    }

    public RecapitulatifClient(String societeFacturation, TypeLivraison typeLivraison, String client, String bonlivraisonMimeType, LocalDate dateBonLivraison, String numeroBonLivraison, String matricule, String produit, Double totalQuantiteeVendue, Unite uniteVente, Double totalPrixVente, boolean facture, String chantier, String fournisseur, Float quantiteAchetee, Unite uniteAchat, Float prixTotalAchat) {
        this(societeFacturation, typeLivraison, client, bonlivraisonMimeType, dateBonLivraison, numeroBonLivraison, matricule, produit, totalQuantiteeVendue, uniteVente, totalPrixVente, facture, chantier);
        this.fournisseur = fournisseur;
        this.quantiteAchetee = quantiteAchetee;
        this.uniteAchat = uniteAchat;
        this.prixTotalAchat = prixTotalAchat;
    }

    public String getClient() {
        return client;
    }

    public LocalDate getDateBonLivraison() {
        return dateBonLivraison;
    }

    public String getNumeroBonLivraison() {
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

    public Unite getUniteVente() {
        return uniteVente;
    }

    public void setUniteVente(Unite uniteVente) {
        this.uniteVente = uniteVente;
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

    public boolean isFacture() {
        return facture;
    }

    public void setFacture(boolean facture) {
        this.facture = facture;
    }

    public String getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(String fournisseur) {
        this.fournisseur = fournisseur;
    }

    public Float getQuantiteAchetee() {
        return quantiteAchetee;
    }

    public void setQuantiteAchetee(Float quantiteAchetee) {
        this.quantiteAchetee = quantiteAchetee;
    }

    public Unite getUniteAchat() {
        return uniteAchat;
    }

    public void setUniteAchat(Unite uniteAchat) {
        this.uniteAchat = uniteAchat;
    }

    public Float getPrixTotalAchat() {
        return prixTotalAchat;
    }

    public void setPrixTotalAchat(Float prixTotalAchat) {
        this.prixTotalAchat = prixTotalAchat;
    }

    public String getChantier() {
        return chantier;
    }

    public void setChantier(String chantier) {
        this.chantier = chantier;
    }

    public static String csvHeader(TypeLivraison typeLivraison) {
        String csvHeader = "client;chantier;dateBonLivraison;numeroBonLivraison;matricule;produit;totalQuantiteeVendue;UniteVente;totalPrixVente;societeFacturation;facture;type";
        if (typeLivraison == TypeLivraison.Marchandise) {
            csvHeader += ";fournisseur;quantiteAchetee;UniteAchat;prixTotalAchat";
        }
        return csvHeader;
    }

    @Override
    public String toCsv() {
        try {
            StringBuilder csv = new StringBuilder();
            csv.append(Optional.ofNullable(client).orElse("Undefined")).append(";")
                .append(Optional.ofNullable(chantier).orElse("Undefined")).append(";")
                .append(dateBonLivraison).append(";")
                .append(numeroBonLivraison).append(";")
                .append(Optional.ofNullable(matricule).orElse("Undefined")).append(";")
                .append(Optional.ofNullable(produit).orElse("Undefined")).append(";")
                .append(StringUtils.replaceChars(Double.toString(Optional.ofNullable(totalQuantiteeVendue).orElse(0.0)), '.', ',')).append(";")
                .append(Optional.ofNullable(uniteVente).map(Unite::name).orElse("Undefined")).append(";")
                .append(StringUtils.replaceChars(Double.toString(Optional.ofNullable(totalPrixVente).orElse(0.0)), '.', ',')).append(";")
                .append(Optional.ofNullable(societeFacturation).orElse("Undefined")).append(";")
                .append(facture).append(";")
                .append(type).append(";");

            if (type == TypeLivraison.Marchandise) {
                csv.append(Optional.ofNullable(fournisseur).orElse("Undefined"))
                    .append(StringUtils.replaceChars(Float.toString(Optional.ofNullable(quantiteAchetee).orElse(0.0F)), '.', ',')).append(";")
                    .append(Optional.ofNullable(uniteAchat).map(Unite::name).orElse("Undefined")).append(";")
                    .append(StringUtils.replaceChars(Float.toString(Optional.ofNullable(prixTotalAchat).orElse(0.0F)), '.', ',')).append(";");
            }
            return csv.toString();
        } catch (Exception ex) {
            return "error;error;error;error;error;error;error;error;error;error;error;error;error;error;error";
        }
    }
}
