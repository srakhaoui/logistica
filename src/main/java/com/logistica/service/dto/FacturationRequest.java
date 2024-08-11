package com.logistica.service.dto;

import com.logistica.domain.enumeration.ModeReglement;
import com.logistica.domain.enumeration.TypeLivraison;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;

public class FacturationRequest {

    @NotNull
    private Long societeId;
    @NotNull
    private TypeLivraison typeLivraison;
    private String chantier;
    @NotNull
    private Long clientId;

    private Long produitId;
    @NotNull
    private LocalDate dateDebut;
    @NotNull
    private LocalDate dateFin;
    private Double montant;
    private Float remise;

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

    public Long getClientId() {
        return clientId;
    }

    public String getChantier() {
        return chantier;
    }

    public void setChantier(String chantier) {
        this.chantier = chantier;
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

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Float getRemise() {
        return remise;
    }

    public void setRemise(Float remise) {
        this.remise = remise;
    }

    public Long getProduitId() {
        return produitId;
    }

    public void setProduitId(Long produitId) {
        this.produitId = produitId;
    }
}
