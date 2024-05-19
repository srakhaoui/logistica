package com.logistica.service.dto;

import com.logistica.domain.enumeration.TypeLivraison;

import java.time.LocalDate;

public class RecapitulatifFacturationClientRequest {
    private long societeId;
    private TypeLivraison typeLivraison;
    private boolean facture;
    private long clientId;
    private String chantier;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private Double montantMax;
    private boolean regleEnEspece;

    public long getSocieteId() {
        return societeId;
    }

    public void setSocieteId(long societeId) {
        this.societeId = societeId;
    }

    public TypeLivraison getTypeLivraison() {
        return typeLivraison;
    }

    public void setTypeLivraison(TypeLivraison typeLivraison) {
        this.typeLivraison = typeLivraison;
    }
    public boolean isFacture() {
        return facture;
    }

    public void setFacture(boolean facture) {
        this.facture = facture;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public String getChantier() {
        return chantier;
    }

    public void setChantier(String chantier) {
        this.chantier = chantier;
    }

    public Double getMontantMax() {
        return montantMax;
    }

    public void setMontantMax(Double montantMax) {
        this.montantMax = montantMax;
    }

    public boolean isRegleEnEspece() {
        return regleEnEspece;
    }

    public void setRegleEnEspece(boolean regleEnEspece) {
        this.regleEnEspece = regleEnEspece;
    }

    public static RecapitulatifFacturationClientRequest from(FacturationRequest facturationRequest, boolean facture, boolean regleEnEspece){
        RecapitulatifFacturationClientRequest recapitulatifFacturationClientRequest = new RecapitulatifFacturationClientRequest();
        recapitulatifFacturationClientRequest.setSocieteId(facturationRequest.getSocieteId());
        recapitulatifFacturationClientRequest.setClientId(facturationRequest.getClientId());
        recapitulatifFacturationClientRequest.setFacture(facture);
        recapitulatifFacturationClientRequest.setTypeLivraison(facturationRequest.getTypeLivraison());
        recapitulatifFacturationClientRequest.setChantier(facturationRequest.getChantier());
        recapitulatifFacturationClientRequest.setDateDebut(facturationRequest.getDateDebut());
        recapitulatifFacturationClientRequest.setDateFin(facturationRequest.getDateFin());
        recapitulatifFacturationClientRequest.setMontantMax(facturationRequest.getMontant());
        recapitulatifFacturationClientRequest.setRegleEnEspece(regleEnEspece);
        return recapitulatifFacturationClientRequest;
    }
    public static RecapitulatifFacturationClientRequest from(ReglementEspeceRequest reglementEspeceRequest, boolean facture, boolean regleEnEspece){
        RecapitulatifFacturationClientRequest recapitulatifFacturationClientRequest = new RecapitulatifFacturationClientRequest();
        recapitulatifFacturationClientRequest.setSocieteId(reglementEspeceRequest.getSocieteId());
        recapitulatifFacturationClientRequest.setClientId(reglementEspeceRequest.getClientId());
        recapitulatifFacturationClientRequest.setFacture(facture);
        recapitulatifFacturationClientRequest.setTypeLivraison(reglementEspeceRequest.getTypeLivraison());
        recapitulatifFacturationClientRequest.setChantier(reglementEspeceRequest.getChantier());
        recapitulatifFacturationClientRequest.setDateDebut(reglementEspeceRequest.getDateDebut());
        recapitulatifFacturationClientRequest.setDateFin(reglementEspeceRequest.getDateFin());
        recapitulatifFacturationClientRequest.setRegleEnEspece(regleEnEspece);
        return recapitulatifFacturationClientRequest;
    }
}
