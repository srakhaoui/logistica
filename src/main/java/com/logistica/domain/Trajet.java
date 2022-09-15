package com.logistica.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A Trajet.
 */
@Entity
@Table(name = "trajet")
@EntityListeners(AuditingEntityListener.class)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Trajet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "depart", nullable = false)
    private String depart;

    @NotNull
    @Column(name = "destination", nullable = false)
    private String destination;

    @Column(name = "description")
    private String description;

    @Column(name = "commission")
    private Float commission;

    @Embedded
    private Audit audit = new Audit();

    public Audit getAudit() {
        return audit;
    }

    public void setAudit(Audit audit) {
        this.audit = audit;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDepart() {
        return depart;
    }

    public Trajet depart(String depart) {
        this.depart = depart;
        return this;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getDestination() {
        return destination;
    }

    public Trajet destination(String destination) {
        this.destination = destination;
        return this;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDescription() {
        return description;
    }

    public Trajet description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getCommission() {
        return commission;
    }

    public Trajet commission(Float commission) {
        this.commission = commission;
        return this;
    }

    public void setCommission(Float commission) {
        this.commission = commission;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Trajet)) {
            return false;
        }
        return id != null && id.equals(((Trajet) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Trajet{" +
            "id=" + getId() +
            ", depart='" + getDepart() + "'" +
            ", destination='" + getDestination() + "'" +
            ", description='" + getDescription() + "'" +
            ", commission=" + getCommission() +
            "}";
    }
}
