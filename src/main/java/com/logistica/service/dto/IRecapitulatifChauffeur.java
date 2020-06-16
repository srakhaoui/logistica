package com.logistica.service.dto;

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
            .append(getCommissionTrajet()).append(";")
            .append(getReparationDivers()).append(";")
            .append(getTrax()).append(";")
            .append(getBalance()).append(";")
            .append(getAvance()).append(";")
            .append(getPenaliteEse()).append(";")
            .append(getPenaliteChfrs()).append(";")
            .append(getFraisEspece()).append(";")
            .append(getRetenu()).append(";")
            .append(getTotalComission());
        return csv.toString();
    }
}
