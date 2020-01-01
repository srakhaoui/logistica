package com.logistica.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.logistica.domain.enumeration.Unite;

/**
 * A TarifTransport.
 */
@Entity
@Table(name = "tarif_transport")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TarifTransport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "unite", nullable = false)
    private Unite unite;

    @NotNull
    @Column(name = "prix", nullable = false)
    private Float prix;

    @ManyToOne
    @JsonIgnoreProperties("tarifTransports")
    private Client client;

    @ManyToOne
    @JsonIgnoreProperties("tarifTransports")
    private Trajet trajet;

    @ManyToOne
    @JsonIgnoreProperties("tarifTransports")
    private Produit produit;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Unite getUnite() {
        return unite;
    }

    public TarifTransport unite(Unite unite) {
        this.unite = unite;
        return this;
    }

    public void setUnite(Unite unite) {
        this.unite = unite;
    }

    public Float getPrix() {
        return prix;
    }

    public TarifTransport prix(Float prix) {
        this.prix = prix;
        return this;
    }

    public void setPrix(Float prix) {
        this.prix = prix;
    }

    public Client getClient() {
        return client;
    }

    public TarifTransport client(Client client) {
        this.client = client;
        return this;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Trajet getTrajet() {
        return trajet;
    }

    public TarifTransport trajet(Trajet trajet) {
        this.trajet = trajet;
        return this;
    }

    public void setTrajet(Trajet trajet) {
        this.trajet = trajet;
    }

    public Produit getProduit() {
        return produit;
    }

    public TarifTransport produit(Produit produit) {
        this.produit = produit;
        return this;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TarifTransport)) {
            return false;
        }
        return id != null && id.equals(((TarifTransport) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TarifTransport{" +
            "id=" + getId() +
            ", unite='" + getUnite() + "'" +
            ", prix=" + getPrix() +
            "}";
    }
}
