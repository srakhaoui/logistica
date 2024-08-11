package com.logistica.service.dto;

import com.logistica.domain.enumeration.TypeLivraison;

import java.time.LocalDate;

public class RecapitulatifFacturationClientRequest {
    private Long societeId;
    private TypeLivraison typeLivraison;
    private Boolean facture;
    private Long clientId;
    private Long produitId;
    private String chantier;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private Double montantMax;
    private Boolean regleEnEspece;

    public Long getSocieteId() {
        return societeId;
    }

    public void setSocieteId(Long societeId) {
        this.societeId = societeId;
    }

    public TypeLivraison getTypeLivraison() {
        return typeLivraison;
    }

    public void setTypeLivraison(TypeLivraison typeLivraison) {
        this.typeLivraison = typeLivraison;
    }
    public Boolean isFacture() {
        return facture;
    }

    public void setFacture(Boolean facture) {
        this.facture = facture;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
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

    public Boolean isRegleEnEspece() {
        return regleEnEspece;
    }

    public void setRegleEnEspece(Boolean regleEnEspece) {
        this.regleEnEspece = regleEnEspece;
    }

    public Long getProduitId() {
        return produitId;
    }

    public void setProduitId(Long produitId) {
        this.produitId = produitId;
    }

    public static RecapitulatifFacturationClientRequest from(FacturationRequest facturationRequest, boolean facture, boolean regleEnEspece){
        RecapitulatifFacturationClientRequest recapitulatifFacturationClientRequest = new RecapitulatifFacturationClientRequest();
        recapitulatifFacturationClientRequest.setSocieteId(facturationRequest.getSocieteId());
        recapitulatifFacturationClientRequest.setClientId(facturationRequest.getClientId());
        recapitulatifFacturationClientRequest.setProduitId(facturationRequest.getProduitId());
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
        recapitulatifFacturationClientRequest.setProduitId(reglementEspeceRequest.getProduitId());
        recapitulatifFacturationClientRequest.setFacture(facture);
        recapitulatifFacturationClientRequest.setTypeLivraison(reglementEspeceRequest.getTypeLivraison());
        recapitulatifFacturationClientRequest.setChantier(reglementEspeceRequest.getChantier());
        recapitulatifFacturationClientRequest.setDateDebut(reglementEspeceRequest.getDateDebut());
        recapitulatifFacturationClientRequest.setDateFin(reglementEspeceRequest.getDateFin());
        recapitulatifFacturationClientRequest.setRegleEnEspece(regleEnEspece);
        return recapitulatifFacturationClientRequest;
    }
}
