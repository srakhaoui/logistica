package com.logistica.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A GasoilTransfert.
 */
@Entity
@Table(name = "gasoil_transfert")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class GasoilTransfert implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gasoilTransfertSequenceGenerator")
    @SequenceGenerator(name = "gasoilTransfertSequenceGenerator", sequenceName = "gasoil-transfert-seq", allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "transfert_date", nullable = false)
    private LocalDate transfertDate;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "quantite", nullable = false)
    private Double quantite;

    @ManyToOne(optional = false)
    @NotNull
    private Depot source;

    @ManyToOne(optional = false)
    @NotNull
    private Depot destination;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getTransfertDate() {
        return transfertDate;
    }

    public GasoilTransfert transfertDate(LocalDate transfertDate) {
        this.transfertDate = transfertDate;
        return this;
    }

    public void setTransfertDate(LocalDate transfertDate) {
        this.transfertDate = transfertDate;
    }

    public Double getQuantite() {
        return quantite;
    }

    public GasoilTransfert quantite(Double quantite) {
        this.quantite = quantite;
        return this;
    }

    public void setQuantite(Double quantite) {
        this.quantite = quantite;
    }

    public Depot getSource() {
        return source;
    }

    public GasoilTransfert source(Depot depot) {
        this.source = depot;
        return this;
    }

    public void setSource(Depot depot) {
        this.source = depot;
    }

    public Depot getDestination() {
        return destination;
    }

    public GasoilTransfert destination(Depot depot) {
        this.destination = depot;
        return this;
    }

    public void setDestination(Depot depot) {
        this.destination = depot;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GasoilTransfert)) {
            return false;
        }
        return id != null && id.equals(((GasoilTransfert) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "GasoilTransfert{" +
            "id=" + getId() +
            ", transfertDate='" + getTransfertDate() + "'" +
            ", quantite=" + getQuantite() +
            "}";
    }
}
