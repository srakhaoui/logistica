package com.logistica.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.logistica.domain.enumeration.Unite;

/**
 * A TarifAchat.
 */
@Entity
@Table(name = "tarif_achat")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TarifAchat implements Serializable {

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
    @JsonIgnoreProperties("tarifAchats")
    private Fournisseur fournisseur;

    @ManyToOne
    @JsonIgnoreProperties("tarifAchats")
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

    public TarifAchat unite(Unite unite) {
        this.unite = unite;
        return this;
    }

    public void setUnite(Unite unite) {
        this.unite = unite;
    }

    public Float getPrix() {
        return prix;
    }

    public TarifAchat prix(Float prix) {
        this.prix = prix;
        return this;
    }

    public void setPrix(Float prix) {
        this.prix = prix;
    }

    public Fournisseur getFournisseur() {
        return fournisseur;
    }

    public TarifAchat fournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
        return this;
    }

    public void setFournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
    }

    public Produit getProduit() {
        return produit;
    }

    public TarifAchat produit(Produit produit) {
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
        if (!(o instanceof TarifAchat)) {
            return false;
        }
        return id != null && id.equals(((TarifAchat) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TarifAchat{" +
            "id=" + getId() +
            ", unite='" + getUnite() + "'" +
            ", prix=" + getPrix() +
            "}";
    }
}
