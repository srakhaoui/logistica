package com.logistica.domain;

import com.logistica.domain.enumeration.Unite;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A DepotAggregat.
 */
@Entity
@Table(name = "depot_aggregat")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DepotAggregat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "stock", nullable = false)
    private Float stock;

    @Enumerated(EnumType.STRING)
    @Column(name = "unite")
    private Unite unite;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

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

    public DepotAggregat stock(Float stock) {
        this.stock = stock;
        return this;
    }

    public void setStock(Float stock) {
        this.stock = stock;
    }

    public String getNom() {
        return nom;
    }

    public DepotAggregat nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Unite getUnite() {
        return unite;
    }

    public void setUnite(Unite unite) {
        this.unite = unite;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DepotAggregat)) {
            return false;
        }
        return id != null && id.equals(((DepotAggregat) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DepotAggregat{" +
            "id=" + getId() +
            ", stock=" + getStock() +
            ", nom='" + getNom() + "'" +
            "}";
    }
}
