package com.logistica.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.util.Optional;

public class RecapitulatifAchat implements ICsvConvertible {
    private String bonLivraisonMimeType;
    private LocalDate dateBonLivraison;
    private Long numeroBonLivraison;
    private String bonCommandeMimeType;
    private LocalDate dateBonCommande;
    private Long numeroBonCommande;
    private String nomFournisseur;
    private String codeProduit;
    private String matricule;
    private Double totalQuantiteAchetee;
    private Double totalQuantiteConvertie;
    private Double totalPrixAchat;

    public RecapitulatifAchat() {
    }

    public RecapitulatifAchat(String bonLivraisonMimeType, LocalDate dateBonLivraison, Long numeroBonLivraison, String bonCommandeMimeType, LocalDate dateBonCommande, Long numeroBonCommande, String nomFournisseur, String codeProduit, String matricule, Double totalQuantiteAchetee, Double totalQuantiteConvertie, Double totalPrixAchat) {
        this.bonLivraisonMimeType = bonLivraisonMimeType;
        this.dateBonLivraison = dateBonLivraison;
        this.numeroBonLivraison = numeroBonLivraison;
        this.bonCommandeMimeType = bonCommandeMimeType;
        this.dateBonCommande = dateBonCommande;
        this.numeroBonCommande = numeroBonCommande;
        this.nomFournisseur = nomFournisseur;
        this.codeProduit = codeProduit;
        this.matricule = matricule;
        this.totalQuantiteAchetee = totalQuantiteAchetee;
        this.totalQuantiteConvertie = totalQuantiteConvertie;
        this.totalPrixAchat = totalPrixAchat;
    }

    public LocalDate getDateBonCommande() {
        return dateBonCommande;
    }

    public Long getNumeroBonCommande() {
        return numeroBonCommande;
    }

    public String getCodeProduit() {
        return codeProduit;
    }

    public Double getTotalQuantiteAchetee() {
        return totalQuantiteAchetee;
    }

    public Double getTotalQuantiteConvertie() {
        return totalQuantiteConvertie;
    }

    public Double getTotalPrixAchat() {
        return totalPrixAchat;
    }

    public String getBonCommandeMimeType() {
        return bonCommandeMimeType;
    }

    public String getBonLivraisonMimeType() {
        return bonLivraisonMimeType;
    }

    public LocalDate getDateBonLivraison() {
        return dateBonLivraison;
    }

    public Long getNumeroBonLivraison() {
        return numeroBonLivraison;
    }

    public String getNomFournisseur() {
        return nomFournisseur;
    }

    public String getMatricule() {
        return matricule;
    }

    @JsonProperty("hasBonCommande")
    public Boolean hasBonCommande() {
        return StringUtils.isNotBlank(bonCommandeMimeType);
    }

    public static String csvHeader() {
        return "dateBonCommande;numeroBonCommande;dateBonLivraison;numeroBonLivraison;nomFournisseur;codeProduit;matricule;totalQuantiteAchetee;totalQuantiteConvertie;totalPrixAchat";
    }

    public String toCsv() {
        final StringBuilder csv = new StringBuilder();
        csv.append(dateBonCommande).append(";")
            .append(numeroBonCommande).append(";")
            .append(dateBonLivraison).append(";")
            .append(numeroBonLivraison).append(";")
            .append(Optional.ofNullable(nomFournisseur).orElse("Undefined")).append(";")
            .append(Optional.ofNullable(codeProduit).orElse("Undefined")).append(";")
            .append(Optional.ofNullable(matricule).orElse("Undefined")).append(";")
            .append(StringUtils.replaceChars(Double.toString(Optional.ofNullable(totalQuantiteAchetee).orElse(0.0)), '.', ',')).append(";")
            .append(StringUtils.replaceChars(Double.toString(Optional.ofNullable(totalQuantiteConvertie).orElse(0.0)), '.', ',')).append(";")
            .append(StringUtils.replaceChars(Double.toString(Optional.ofNullable(totalPrixAchat).orElse(0.0)), '.', ','));
        return csv.toString();
    }
}
