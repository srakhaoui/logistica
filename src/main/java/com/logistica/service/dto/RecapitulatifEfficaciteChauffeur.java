package com.logistica.service.dto;

import org.apache.commons.lang3.StringUtils;

public class RecapitulatifEfficaciteChauffeur implements ICsvConvertible {

    public static final String CSV_HEADER = "prenomChauffeur;nomChauffeur;nombreTrajets;totalComission;totalPrixVente";

    private Long id;

    private String societe;

    private String prenomChauffeur;

    private String nomChauffeur;

    private Long nombreTrajets;

    private Double totalComission;

    private Double totalPrixVente;

    public RecapitulatifEfficaciteChauffeur(String societe, Long id, String prenomChauffeur, String nomChauffeur, Long nombreTrajets, Double totalComission, Double totalPrixVente) {
        this.societe = societe;
        this.id = id;
        this.prenomChauffeur = prenomChauffeur;
        this.nomChauffeur = nomChauffeur;
        this.nombreTrajets = nombreTrajets;
        this.totalComission = totalComission;
        this.totalPrixVente = totalPrixVente;
    }

    public String toCsv() {
        StringBuilder csv = new StringBuilder();
        csv.append(getPrenomChauffeur()).append(";")
            .append(getNomChauffeur()).append(";")
            .append(getNombreTrajets()).append(";")
            .append(StringUtils.replaceChars(Double.toString(getTotalComission()), '.', ',')).append(";")
            .append(StringUtils.replaceChars(Double.toString(getTotalPrixVente()), '.', ',')).append(";");
        return csv.toString();
    }

    public Long getId() {
        return id;
    }

    public String getSociete() {
        return societe;
    }

    public String getPrenomChauffeur() {
        return prenomChauffeur;
    }

    public String getNomChauffeur() {
        return nomChauffeur;
    }

    public Long getNombreTrajets() {
        return nombreTrajets;
    }

    public Double getTotalComission() {
        return totalComission;
    }

    public Double getTotalPrixVente() {
        return totalPrixVente;
    }
}
