package com.logistica.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.logistica.domain.enumeration.TypeLivraison;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.util.Optional;

public class RecapitulatifClient implements ICsvConvertible {
    private String client;
    private String bonlivraisonMimeType;
    private LocalDate dateBonLivraison;
    private boolean facture;
    private Long numeroBonLivraison;
    private String matricule;
    private String produit;
    private Double totalQuantiteeVendue;
    private Double totalPrixVente;
    private String societeFacturation;
    private TypeLivraison type;
    private String fournisseur;
    private Float quantiteAchetee;
    private Float prixTotalAchat;

    public RecapitulatifClient(String societeFacturation, TypeLivraison typeLivraison, String client, String bonlivraisonMimeType, LocalDate dateBonLivraison, Long numeroBonLivraison, String matricule, String produit, Double totalQuantiteeVendue, Double totalPrixVente, boolean facture, String fournisseur, Float quantiteAchetee, Float prixTotalAchat) {
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
        this.facture = facture;
        this.fournisseur = fournisseur;
        this.quantiteAchetee = quantiteAchetee;
        this.prixTotalAchat = prixTotalAchat;
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

    public Float getPrixTotalAchat() {
        return prixTotalAchat;
    }

    public void setPrixTotalAchat(Float prixTotalAchat) {
        this.prixTotalAchat = prixTotalAchat;
    }

    public static String csvHeader() {
        return "client;dateBonLivraison;numeroBonLivraison;matricule;produit;totalQuantiteeVendue;totalPrixVente;societeFacturation;facture;type;quantiteAchetee;prixTotalAchat;fournisseur";
    }

    @Override
    public String toCsv() {
        try {
            StringBuilder csv = new StringBuilder();
            csv.append(Optional.ofNullable(client).orElse("Undefined")).append(";")
                .append(dateBonLivraison).append(";")
                .append(numeroBonLivraison).append(";")
                .append(Optional.ofNullable(matricule).orElse("Undefined")).append(";")
                .append(Optional.ofNullable(produit).orElse("Undefined")).append(";")
                .append(StringUtils.replaceChars(Double.toString(Optional.ofNullable(totalQuantiteeVendue).orElse(0.0)), '.', ',')).append(";")
                .append(StringUtils.replaceChars(Double.toString(Optional.ofNullable(totalPrixVente).orElse(0.0)), '.', ',')).append(";")
                .append(Optional.ofNullable(societeFacturation).orElse("Undefined")).append(";")
                .append(facture).append(";")
                .append(type).append(";")
                .append(quantiteAchetee).append(";")
                .append(prixTotalAchat).append(";")
                .append(fournisseur);

            return csv.toString();
        } catch (Exception ex) {
            return "error;error;error;error;error;error;error;error;error;error";
        }
    }
}
