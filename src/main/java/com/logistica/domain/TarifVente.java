package com.logistica.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.logistica.domain.enumeration.Unite;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A TarifVente.
 */
@Entity
@Table(name = "tarif_vente")
@EntityListeners(AuditingEntityListener.class)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TarifVente implements Serializable {

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
    @JsonIgnoreProperties("tarifVentes")
    private Client client;

    @ManyToOne
    @JsonIgnoreProperties("tarifVentes")
    private Produit produit;

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

    public Unite getUnite() {
        return unite;
    }

    public TarifVente unite(Unite unite) {
        this.unite = unite;
        return this;
    }

    public void setUnite(Unite unite) {
        this.unite = unite;
    }

    public Float getPrix() {
        return prix;
    }

    public TarifVente prix(Float prix) {
        this.prix = prix;
        return this;
    }

    public void setPrix(Float prix) {
        this.prix = prix;
    }

    public Client getClient() {
        return client;
    }

    public TarifVente client(Client client) {
        this.client = client;
        return this;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Produit getProduit() {
        return produit;
    }

    public TarifVente produit(Produit produit) {
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
        if (!(o instanceof TarifVente)) {
            return false;
        }
        return id != null && id.equals(((TarifVente) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TarifVente{" +
            "id=" + getId() +
            ", unite='" + getUnite() + "'" +
            ", prix=" + getPrix() +
            "}";
    }
}
