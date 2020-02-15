package com.logistica.service.dto;

public interface IRecapitulatifChauffeur {
    String getPrenomChauffeur();
    String getNomChauffeur();
    String getDescription();
    Integer getNombreTrajets();
    Double GetCommissionTrajet();
    Double getReparationDivers();
    Double getTrax();
    Double getBalance();
    Double getAvance();
    Double getPenaliteEse();
    Double getPenaliteChfrs();
    Double getFraisEspece();
    Double getRetenu();
    Double getTotalComission();
}
