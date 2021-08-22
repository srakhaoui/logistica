package com.logistica.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.logistica.domain.enumeration.UniteGasoilGros;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A GasoilVenteGros.
 */
@Entity
@Table(name = "gasoil_vente_gros")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class GasoilVenteGros implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "date_vente", nullable = false)
    private LocalDate dateVente;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "prix_vente_unitaire", nullable = false)
    private Float prixVenteUnitaire;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "quantite", nullable = false)
    private Float quantite;

    @Column(name = "prix_vente_total", nullable = false)
    private Float prixVenteTotal;

    @Column(name = "marge_globale", nullable = false)
    private Float margeGlobale;

    @Column(name = "taux_marge", nullable = false)
    private Float tauxMarge;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "unite_gasoil_gros", nullable = false)
    private UniteGasoilGros uniteGasoilGros;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("gasoilVenteGros")
    private Societe transporteur;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("gasoilVenteGros")
    private ClientGrossiste client;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("gasoilVenteGros")
    private GasoilAchatGros achatGasoil;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getPrixVenteUnitaire() {
        return prixVenteUnitaire;
    }

    public GasoilVenteGros prixVenteUnitaire(Float prixVenteUnitaire) {
        this.prixVenteUnitaire = prixVenteUnitaire;
        return this;
    }

    public void setPrixVenteUnitaire(Float prixVenteUnitaire) {
        this.prixVenteUnitaire = prixVenteUnitaire;
    }

    public Float getQuantite() {
        return quantite;
    }

    public GasoilVenteGros quantite(Float quantite) {
        this.quantite = quantite;
        return this;
    }

    public void setQuantite(Float quantite) {
        this.quantite = quantite;
    }

    public Float getPrixVenteTotal() {
        return prixVenteTotal;
    }

    public GasoilVenteGros prixVenteTotal(Float prixVenteTotal) {
        this.prixVenteTotal = prixVenteTotal;
        return this;
    }

    public void setPrixVenteTotal(Float prixVenteTotal) {
        this.prixVenteTotal = prixVenteTotal;
    }

    public Float getMargeGlobale() {
        return margeGlobale;
    }

    public GasoilVenteGros margeGlobale(Float margeGlobale) {
        this.margeGlobale = margeGlobale;
        return this;
    }

    public void setMargeGlobale(Float margeGlobale) {
        this.margeGlobale = margeGlobale;
    }

    public Float getTauxMarge() {
        return tauxMarge;
    }

    public GasoilVenteGros tauxMarge(Float tauxMarge) {
        this.tauxMarge = tauxMarge;
        return this;
    }

    public void setTauxMarge(Float tauxMarge) {
        this.tauxMarge = tauxMarge;
    }

    public UniteGasoilGros getUniteGasoilGros() {
        return uniteGasoilGros;
    }

    public GasoilVenteGros uniteGasoilGros(UniteGasoilGros uniteGasoilGros) {
        this.uniteGasoilGros = uniteGasoilGros;
        return this;
    }

    public void setUniteGasoilGros(UniteGasoilGros uniteGasoilGros) {
        this.uniteGasoilGros = uniteGasoilGros;
    }

    public Societe getTransporteur() {
        return transporteur;
    }

    public GasoilVenteGros societeFacturation(Societe societe) {
        this.transporteur = societe;
        return this;
    }

    public void setTransporteur(Societe societe) {
        this.transporteur = societe;
    }

    public ClientGrossiste getClient() {
        return client;
    }

    public GasoilVenteGros client(ClientGrossiste clientGrossiste) {
        this.client = clientGrossiste;
        return this;
    }

    public LocalDate getDateVente() {
        return dateVente;
    }

    public void setDateVente(LocalDate dateVente) {
        this.dateVente = dateVente;
    }

    public void setClient(ClientGrossiste clientGrossiste) {
        this.client = clientGrossiste;
    }

    public GasoilAchatGros getAchatGasoil() {
        return achatGasoil;
    }

    public GasoilVenteGros achatGasoil(GasoilAchatGros gasoilAchatGros) {
        this.achatGasoil = gasoilAchatGros;
        return this;
    }

    public void setAchatGasoil(GasoilAchatGros gasoilAchatGros) {
        this.achatGasoil = gasoilAchatGros;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GasoilVenteGros)) {
            return false;
        }
        return id != null && id.equals(((GasoilVenteGros) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "GasoilVenteGros{" +
            "id=" + getId() +
            ", prixVenteUnitaire=" + getPrixVenteUnitaire() +
            ", quantite=" + getQuantite() +
            ", prixVenteTotal=" + getPrixVenteTotal() +
            ", margeGlobale=" + getMargeGlobale() +
            ", tauxMarge=" + getTauxMarge() +
            ", uniteGasoilGros='" + getUniteGasoilGros() + "'" +
            "}";
    }
}
