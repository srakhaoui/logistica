package com.logistica.service.dto;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

public class RecapitulatifChargeGasoil implements ICsvConvertible {
    private final Long societeId;
    private final String societe;
    private final Long transporteurId;
    private final String nomTransporteur;
    private final String prenomTransporteur;
    private final String matricule;
    private final Double totalQuantiteEnLitre;
    private final Double moyennePrixDuLitre;
    private final Double totalPrixGasoil;
    private final Long kilometrageParcouru;
    private Double margeGasoil;
    private Double totalCommissionChauffeur;
    private Double totalVenteTransporteur;

    public RecapitulatifChargeGasoil(Long societeId, String societe, Long transporteurId, String nomTransporteur, String prenomTransporteur, String matricule, Double totalQuantiteEnLitre, Double moyennePrixDuLitre, Double totalPrixGasoil, Long kilometrageParcouru) {
        this.societeId = societeId;
        this.societe = societe;
        this.transporteurId = transporteurId;
        this.nomTransporteur = nomTransporteur;
        this.prenomTransporteur = prenomTransporteur;
        this.matricule = matricule;
        this.totalQuantiteEnLitre = round(totalQuantiteEnLitre);
        this.moyennePrixDuLitre = round(moyennePrixDuLitre);
        this.totalPrixGasoil = round(totalPrixGasoil);
        this.kilometrageParcouru = kilometrageParcouru;
    }

    private double round(Double totalQuantiteEnLitre) {
        return BigDecimal.valueOf(totalQuantiteEnLitre).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
    }

    public Long getSocieteId() {
        return societeId;
    }

    public String getSociete() {
        return societe;
    }

    public String getMatricule() {
        return matricule;
    }

    public Double getTotalQuantiteEnLitre() {
        return totalQuantiteEnLitre;
    }

    public Double getMoyennePrixDuLitre() {
        return moyennePrixDuLitre;
    }

    public Double getTotalPrixGasoil() {
        return totalPrixGasoil;
    }

    public Long getKilometrageParcouru() {
        return kilometrageParcouru;
    }

    public Long getTransporteurId() {
        return transporteurId;
    }

    public Double getMargeGasoil() {
        return margeGasoil;
    }

    public void setMargeGasoil(Double margeGasoil) {
        this.margeGasoil = margeGasoil;
    }

    public Double getTotalCommissionChauffeur() {
        return totalCommissionChauffeur;
    }

    public void setTotalCommissionChauffeur(Double totalCommissionChauffeur) {
        this.totalCommissionChauffeur = totalCommissionChauffeur;
    }

    public String getNomTransporteur() {
        return nomTransporteur;
    }

    public String getPrenomTransporteur() {
        return prenomTransporteur;
    }

    public Double getTotalVenteTransporteur() {
        return totalVenteTransporteur;
    }

    public void setTotalVenteTransporteur(Double totalVenteTransporteur) {
        this.totalVenteTransporteur = totalVenteTransporteur;
    }

    public static String csvHeader() {
        return "societe;nomTransporteur;prenomTransporteur;matricule;totalQuantiteEnLitre;moyennePrixDuLitre;totalPrixGasoil;kilometrageParcouru;margeGasoil;totalCommissionChauffeur;totalVenteTransporteur";
    }

    @Override
    public String toCsv() {
        StringBuilder csv = new StringBuilder();
        csv.append(Optional.ofNullable(societe).orElse("Undefined")).append(";")
            .append(Optional.ofNullable(nomTransporteur).orElse("Undefined")).append(";")
            .append(Optional.ofNullable(prenomTransporteur).orElse("Undefined")).append(";")
            .append(Optional.ofNullable(matricule).orElse("Undefined")).append(";")
            .append(StringUtils.replaceChars(Double.toString(Optional.ofNullable(totalQuantiteEnLitre).orElse(0.0)), '.', ',')).append(";")
            .append(StringUtils.replaceChars(Double.toString(Optional.ofNullable(moyennePrixDuLitre).orElse(0.0)), '.', ',')).append(";")
            .append(StringUtils.replaceChars(Double.toString(Optional.ofNullable(totalPrixGasoil).orElse(0.0)), '.', ',')).append(";")
            .append(StringUtils.replaceChars(Double.toString(Optional.ofNullable(kilometrageParcouru).orElse(0L)), '.', ',')).append(";")
            .append(StringUtils.replaceChars(Double.toString(Optional.ofNullable(margeGasoil).orElse(0.0)), '.', ',')).append(";")
            .append(StringUtils.replaceChars(Double.toString(Optional.ofNullable(totalCommissionChauffeur).orElse(0.0)), '.', ',')).append(";")
            .append(StringUtils.replaceChars(Double.toString(Optional.ofNullable(totalPrixGasoil).orElse(0.0)), '.', ',')).append(";")
            .append(StringUtils.replaceChars(Double.toString(Optional.ofNullable(totalVenteTransporteur).orElse(0.0)), '.', ','));
        return csv.toString();
    }
}

