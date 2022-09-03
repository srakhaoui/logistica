package com.logistica.domain;

import com.logistica.domain.enumeration.Unite;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A AgregatTransfert.
 */
@Entity
@Table(name = "agregat_transfert")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AgregatTransfert implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "transfert_date", nullable = false)
    private LocalDate transfertDate;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "quantite", nullable = false)
    private Float quantite;

    @Enumerated(EnumType.STRING)
    @Column(name = "unite")
    private Unite unite;


    @ManyToOne(optional = false)
    @NotNull
    private DepotAggregat source;

    @ManyToOne(optional = false)
    @NotNull
    private DepotAggregat destination;

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

    public AgregatTransfert transfertDate(LocalDate transfertDate) {
        this.transfertDate = transfertDate;
        return this;
    }

    public void setTransfertDate(LocalDate transfertDate) {
        this.transfertDate = transfertDate;
    }

    public Float getQuantite() {
        return quantite;
    }

    public AgregatTransfert quantite(Float quantite) {
        this.quantite = quantite;
        return this;
    }

    public void setQuantite(Float quantite) {
        this.quantite = quantite;
    }

    public DepotAggregat getSource() {
        return source;
    }

    public AgregatTransfert source(DepotAggregat depotAggregat) {
        this.source = depotAggregat;
        return this;
    }

    public void setSource(DepotAggregat depotAggregat) {
        this.source = depotAggregat;
    }

    public DepotAggregat getDestination() {
        return destination;
    }

    public AgregatTransfert destination(DepotAggregat depotAggregat) {
        this.destination = depotAggregat;
        return this;
    }

    public void setDestination(DepotAggregat depotAggregat) {
        this.destination = depotAggregat;
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
        if (!(o instanceof AgregatTransfert)) {
            return false;
        }
        return id != null && id.equals(((AgregatTransfert) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AgregatTransfert{" +
            "id=" + getId() +
            ", transfertDate='" + getTransfertDate() + "'" +
            ", quantite=" + getQuantite() +
            "}";
    }

    @PrePersist
    public void prePersist() {
        transfertDate = LocalDate.now();
    }
}
