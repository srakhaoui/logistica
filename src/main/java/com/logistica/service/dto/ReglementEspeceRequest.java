package com.logistica.service.dto;

import com.logistica.domain.enumeration.TypeLivraison;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class ReglementEspeceRequest {

    @NotNull
    private Long societeId;
    @NotNull
    private TypeLivraison typeLivraison;
    private String chantier;
    @NotNull
    private Long clientId;
    @NotNull
    private Long produitId;
    @NotNull
    private LocalDate dateDebut;
    @NotNull
    private LocalDate dateFin;

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

    public long getClientId() {
        return clientId;
    }

    public String getChantier() {
        return chantier;
    }

    public void setChantier(String chantier) {
        this.chantier = chantier;
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

    public Long getProduitId() {
        return produitId;
    }
    public void setProduitId(Long produitId) {
        this.produitId = produitId;
    }
}
