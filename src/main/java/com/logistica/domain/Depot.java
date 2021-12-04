package com.logistica.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A Depot.
 */
@Entity
@Table(name = "depot")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Depot implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "stock", nullable = false)
    private Float stock;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "consommation_interne")
    private Boolean consommationInterne;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("depots")
    private ClientGrossiste alimentation;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("depots")
    private FournisseurGrossiste consommation;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getStock() {
        return stock;
    }

    public Depot stock(Float stock) {
        this.stock = stock;
        return this;
    }

    public void setStock(Float stock) {
        this.stock = stock;
    }

    public String getNom() {
        return nom;
    }

    public Depot nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Boolean isConsommationInterne() {
        return consommationInterne;
    }

    public Depot consommationInterne(Boolean consommationInterne) {
        this.consommationInterne = consommationInterne;
        return this;
    }

    public void setConsommationInterne(Boolean consommationInterne) {
        this.consommationInterne = consommationInterne;
    }

    public ClientGrossiste getAlimentation() {
        return alimentation;
    }

    public Depot alimentation(ClientGrossiste clientGrossiste) {
        this.alimentation = clientGrossiste;
        return this;
    }

    public void setAlimentation(ClientGrossiste clientGrossiste) {
        this.alimentation = clientGrossiste;
    }

    public FournisseurGrossiste getConsommation() {
        return consommation;
    }

    public Depot consommation(FournisseurGrossiste fournisseurGrossiste) {
        this.consommation = fournisseurGrossiste;
        return this;
    }

    public void setConsommation(FournisseurGrossiste fournisseurGrossiste) {
        this.consommation = fournisseurGrossiste;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Depot)) {
            return false;
        }
        return id != null && id.equals(((Depot) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Depot{" +
            "id=" + getId() +
            ", stock=" + getStock() +
            ", nom='" + getNom() + "'" +
            ", consommationInterne='" + isConsommationInterne() + "'" +
            "}";
    }
}
