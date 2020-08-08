package com.logistica.service.dto;

public class RecapitulatifChargeGasoil {
    private final Long societeId;
    private final String societe;
    private final String matricule;
    private final Double totalQuantiteEnLitre;
    private final Double moyennePrixDuLitre;
    private final Double totalPrixGasoil;
    private final Long kilometrageParcouru;
    private Double margeGasoil;

    public RecapitulatifChargeGasoil(Long societeId, String societe, String matricule, Double totalQuantiteEnLitre, Double moyennePrixDuLitre, Double totalPrixGasoil, Long kilometrageParcouru) {
        this.societeId = societeId;
        this.societe = societe;
        this.matricule = matricule;
        this.totalQuantiteEnLitre = totalQuantiteEnLitre;
        this.moyennePrixDuLitre = moyennePrixDuLitre;
        this.totalPrixGasoil = totalPrixGasoil;
        this.kilometrageParcouru = kilometrageParcouru;
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

    public Double getMargeGasoil() {
        return margeGasoil;
    }

    public void setMargeGasoil(Double margeGasoil) {
        this.margeGasoil = margeGasoil;
    }
}

