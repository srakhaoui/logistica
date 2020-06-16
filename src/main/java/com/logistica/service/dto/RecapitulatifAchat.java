package com.logistica.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;

public class RecapitulatifAchat implements ICsvConvertible {
    private Long livraisonId;
    private String bonCommandeMimeType;
    private LocalDate dateBonCommande;
    private Integer numeroBonCommande;
    private String codeProduit;
    private Double totalQuantiteAchetee;
    private Double totalQuantiteConvertie;
    private Double totalPrixAchat;

    public RecapitulatifAchat() {
    }

    public RecapitulatifAchat(Long livraisonId, String bonCommandeMimeType, LocalDate dateBonCommande, Integer numeroBonCommande, String codeProduit, Double totalQuantiteAchetee, Double totalQuantiteConvertie, Double totalPrixAchat) {
        this.livraisonId = livraisonId;
        this.bonCommandeMimeType = bonCommandeMimeType;
        this.dateBonCommande = dateBonCommande;
        this.numeroBonCommande = numeroBonCommande;
        this.codeProduit = codeProduit;
        this.totalQuantiteAchetee = totalQuantiteAchetee;
        this.totalQuantiteConvertie = totalQuantiteConvertie;
        this.totalPrixAchat = totalPrixAchat;
    }

    public LocalDate getDateBonCommande() {
        return dateBonCommande;
    }

	public Integer getNumeroBonCommande() {
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

    public Long getLivraisonId() {
        return livraisonId;
    }

    public String getBonCommandeMimeType() {
        return bonCommandeMimeType;
    }

    @JsonProperty("hasBonCommande")
    public Boolean hasBonCommande() {
        return StringUtils.isNotBlank(bonCommandeMimeType);
    }

    public static String csvHeader() {
        return "dateBonCommande;numeroBonCommande;codeProduit;totalQuantiteAchetee;totalQuantiteConvertie;totalPrixAchat";
    }

    public String toCsv() {
        final StringBuilder csv = new StringBuilder();
        csv.append(dateBonCommande).append(";")
            .append(numeroBonCommande).append(";")
            .append(codeProduit).append(";")
            .append(totalQuantiteAchetee).append(";")
            .append(totalQuantiteConvertie).append(";")
            .append(totalPrixAchat);
        return csv.toString();
    }
}
