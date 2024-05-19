package com.logistica.service.dto;

import com.logistica.domain.enumeration.TypeLivraison;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class ReglementEspeceRequest {

    @NotNull
    private long societeId;
    @NotNull
    private TypeLivraison typeLivraison;
    private String chantier;
    @NotNull
    private long clientId;
    @NotNull
    private LocalDate dateDebut;
    @NotNull
    private LocalDate dateFin;
    private boolean regleEnEspece;

    private boolean facture;

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

    public boolean isRegleEnEspece() {
        return regleEnEspece;
    }

    public void setRegleEnEspece(boolean regleEnEspece) {
        this.regleEnEspece = regleEnEspece;
    }

    public boolean isFacture() {
        return facture;
    }

    public void setFacture(boolean facture) {
        this.facture = facture;
    }
}
