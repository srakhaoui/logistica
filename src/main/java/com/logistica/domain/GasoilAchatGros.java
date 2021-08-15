package com.logistica.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.logistica.domain.enumeration.UniteGasoilGros;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A GasoilAchatGros.
 */
@Entity
@Table(name = "gasoil_achat_gros")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class GasoilAchatGros implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gasoilAchatGrosSequenceGenerator")
    @SequenceGenerator(name = "gasoilAchatGrosSequenceGenerator", sequenceName = "gasoil-achat-gros-seq", allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "date_reception", nullable = false)
    private LocalDate dateReception;

    @NotNull
    @Size(min = 1)
    @Column(name = "numero_bon_reception", nullable = false, unique = true)
    private String numeroBonReception;

    @Column(name = "description")
    private String description;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "quantity", nullable = false)
    private Float quantity;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "prix_unitaire", nullable = false)
    private Float prixUnitaire;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "unite_gasoil_gros", nullable = false)
    private UniteGasoilGros uniteGasoilGros;

    @ManyToOne(optional = false)
    @JsonIgnoreProperties("gasoilAchatGros")
    private FournisseurGrossiste fournisseurGrossiste;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("gasoilAchatGros")
    private Societe transporteur;

    @ManyToOne(optional = false)
    @JsonIgnoreProperties("gasoilAchatGros")
    private Carburant carburant;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateReception() {
        return dateReception;
    }

    public GasoilAchatGros dateReception(LocalDate dateReception) {
        this.dateReception = dateReception;
        return this;
    }

    public void setDateReception(LocalDate dateReception) {
        this.dateReception = dateReception;
    }

    public String getNumeroBonReception() {
        return numeroBonReception;
    }

    public GasoilAchatGros numeroBonReception(String numeroBonReception) {
        this.numeroBonReception = numeroBonReception;
        return this;
    }

    public void setNumeroBonReception(String numeroBonReception) {
        this.numeroBonReception = numeroBonReception;
    }

    public String getDescription() {
        return description;
    }

    public GasoilAchatGros description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getQuantity() {
        return quantity;
    }

    public GasoilAchatGros quantity(Float quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public Float getPrixUnitaire() {
        return prixUnitaire;
    }

    public GasoilAchatGros prixUnitaire(Float prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
        return this;
    }

    public void setPrixUnitaire(Float prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public UniteGasoilGros getUniteGasoilGros() {
        return uniteGasoilGros;
    }

    public GasoilAchatGros uniteGasoilGros(UniteGasoilGros uniteGasoilGros) {
        this.uniteGasoilGros = uniteGasoilGros;
        return this;
    }

    public void setUniteGasoilGros(UniteGasoilGros uniteGasoilGros) {
        this.uniteGasoilGros = uniteGasoilGros;
    }

    public FournisseurGrossiste getFournisseurGrossiste() {
        return fournisseurGrossiste;
    }

    public GasoilAchatGros fournisseur(FournisseurGrossiste fournisseur) {
        this.fournisseurGrossiste = fournisseur;
        return this;
    }

    public void setFournisseurGrossiste(FournisseurGrossiste fournisseurGrossiste) {
        this.fournisseurGrossiste = fournisseurGrossiste;
    }

    public Societe getTransporteur() {
        return transporteur;
    }

    public GasoilAchatGros transporteur(Societe societe) {
        this.transporteur = societe;
        return this;
    }

    public void setTransporteur(Societe societe) {
        this.transporteur = societe;
    }

    public Carburant getCarburant() {
        return carburant;
    }

    public GasoilAchatGros carburant(Carburant carburant) {
        this.carburant = carburant;
        return this;
    }

    public void setCarburant(Carburant carburant) {
        this.carburant = carburant;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GasoilAchatGros)) {
            return false;
        }
        return id != null && id.equals(((GasoilAchatGros) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "GasoilAchatGros{" +
            "id=" + getId() +
            ", dateReception='" + getDateReception() + "'" +
            ", numeroBonReception='" + getNumeroBonReception() + "'" +
            ", description='" + getDescription() + "'" +
            ", quantity=" + getQuantity() +
            ", prixUnitaire=" + getPrixUnitaire() +
            ", uniteGasoilGros='" + getUniteGasoilGros() + "'" +
            "}";
    }
}
