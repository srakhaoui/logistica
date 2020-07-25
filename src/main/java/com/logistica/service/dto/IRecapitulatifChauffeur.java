package com.logistica.service.dto;

import org.apache.commons.lang3.StringUtils;

public interface IRecapitulatifChauffeur extends ICsvConvertible {
    Long getId();

    String getPrenomChauffeur();

    String getNomChauffeur();

    String getDescription();

    Integer getNombreTrajets();

    Double getCommissionTrajet();

    Double getReparationDivers();

    Double getTrax();

    Double getBalance();

    Double getAvance();

    Double getPenaliteEse();

    Double getPenaliteChfrs();

    Double getFraisEspece();

    Double getRetenu();

    Double getTotalComission();

    String CSV_HEADER = "prenomChauffeur;nomChauffeur;nombreTrajets;commissionTrajet;reparationDivers;trax;balance;avance;penaliteEse;penaliteChfrs;fraisEspece;retenu;totalComission";

    default String toCsv() {
        StringBuilder csv = new StringBuilder();
        csv.append(getPrenomChauffeur()).append(";")
            .append(getNomChauffeur()).append(";")
            .append(getNombreTrajets()).append(";")
            .append(StringUtils.replaceChars(Double.toString(getCommissionTrajet()), '.', ',')).append(";")
            .append(StringUtils.replaceChars(Double.toString(getReparationDivers()), '.', ',')).append(";")
            .append(StringUtils.replaceChars(Double.toString(getTrax()), '.', ',')).append(";")
            .append(StringUtils.replaceChars(Double.toString(getBalance()), '.', ',')).append(";")
            .append(StringUtils.replaceChars(Double.toString(getAvance()), '.', ',')).append(";")
            .append(StringUtils.replaceChars(Double.toString(getPenaliteEse()), '.', ',')).append(";")
            .append(StringUtils.replaceChars(Double.toString(getPenaliteChfrs()), '.', ',')).append(";")
            .append(StringUtils.replaceChars(Double.toString(getFraisEspece()), '.', ',')).append(";")
            .append(StringUtils.replaceChars(Double.toString(getRetenu()), '.', ',')).append(";")
            .append(StringUtils.replaceChars(Double.toString(getTotalComission()), '.', ','));
        return csv.toString();
    }
}
