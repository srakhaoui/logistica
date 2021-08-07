package com.logistica.service.dto;

import com.logistica.domain.enumeration.UniteGasoilGros;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the {@link com.logistica.domain.GasoilAchatGros} entity. This class is used
 * in {@link com.logistica.web.rest.GasoilAchatGrosResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /gasoil-achat-gros?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class GasoilAchatGrosCriteria implements Serializable, Criteria {
    /**
     * Class for filtering UniteGasoilGros
     */
    public static class UniteGasoilGrosFilter extends Filter<UniteGasoilGros> {

        public UniteGasoilGrosFilter() {
        }

        public UniteGasoilGrosFilter(UniteGasoilGrosFilter filter) {
            super(filter);
        }

        @Override
        public UniteGasoilGrosFilter copy() {
            return new UniteGasoilGrosFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter dateReception;

    private StringFilter numeroBonReception;

    private StringFilter description;

    private FloatFilter quantity;

    private FloatFilter prixUnitaire;

    private UniteGasoilGrosFilter uniteGasoilGros;

    private LongFilter fournisseurId;

    private LongFilter transporteurId;

    private LongFilter produitId;

    public GasoilAchatGrosCriteria() {
    }

    public GasoilAchatGrosCriteria(GasoilAchatGrosCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.dateReception = other.dateReception == null ? null : other.dateReception.copy();
        this.numeroBonReception = other.numeroBonReception == null ? null : other.numeroBonReception.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.quantity = other.quantity == null ? null : other.quantity.copy();
        this.prixUnitaire = other.prixUnitaire == null ? null : other.prixUnitaire.copy();
        this.uniteGasoilGros = other.uniteGasoilGros == null ? null : other.uniteGasoilGros.copy();
        this.fournisseurId = other.fournisseurId == null ? null : other.fournisseurId.copy();
        this.transporteurId = other.transporteurId == null ? null : other.transporteurId.copy();
        this.produitId = other.produitId == null ? null : other.produitId.copy();
    }

    @Override
    public GasoilAchatGrosCriteria copy() {
        return new GasoilAchatGrosCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getDateReception() {
        return dateReception;
    }

    public void setDateReception(LocalDateFilter dateReception) {
        this.dateReception = dateReception;
    }

    public StringFilter getNumeroBonReception() {
        return numeroBonReception;
    }

    public void setNumeroBonReception(StringFilter numeroBonReception) {
        this.numeroBonReception = numeroBonReception;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public FloatFilter getQuantity() {
        return quantity;
    }

    public void setQuantity(FloatFilter quantity) {
        this.quantity = quantity;
    }

    public FloatFilter getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(FloatFilter prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public UniteGasoilGrosFilter getUniteGasoilGros() {
        return uniteGasoilGros;
    }

    public void setUniteGasoilGros(UniteGasoilGrosFilter uniteGasoilGros) {
        this.uniteGasoilGros = uniteGasoilGros;
    }

    public LongFilter getFournisseurId() {
        return fournisseurId;
    }

    public void setFournisseurId(LongFilter fournisseurId) {
        this.fournisseurId = fournisseurId;
    }

    public LongFilter getTransporteurId() {
        return transporteurId;
    }

    public void setTransporteurId(LongFilter transporteurId) {
        this.transporteurId = transporteurId;
    }

    public LongFilter getProduitId() {
        return produitId;
    }

    public void setProduitId(LongFilter produitId) {
        this.produitId = produitId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final GasoilAchatGrosCriteria that = (GasoilAchatGrosCriteria) o;
        return
            Objects.equals(id, that.id) &&
                Objects.equals(dateReception, that.dateReception) &&
                Objects.equals(numeroBonReception, that.numeroBonReception) &&
                Objects.equals(description, that.description) &&
                Objects.equals(quantity, that.quantity) &&
                Objects.equals(prixUnitaire, that.prixUnitaire) &&
                Objects.equals(uniteGasoilGros, that.uniteGasoilGros) &&
                Objects.equals(fournisseurId, that.fournisseurId) &&
                Objects.equals(transporteurId, that.transporteurId) &&
                Objects.equals(produitId, that.produitId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            dateReception,
            numeroBonReception,
            description,
            quantity,
            prixUnitaire,
            uniteGasoilGros,
            fournisseurId,
            transporteurId,
            produitId
        );
    }

    @Override
    public String toString() {
        return "GasoilAchatGrosCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (dateReception != null ? "dateReception=" + dateReception + ", " : "") +
            (numeroBonReception != null ? "numeroBonReception=" + numeroBonReception + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (quantity != null ? "quantity=" + quantity + ", " : "") +
            (prixUnitaire != null ? "prixUnitaire=" + prixUnitaire + ", " : "") +
            (uniteGasoilGros != null ? "uniteGasoilGros=" + uniteGasoilGros + ", " : "") +
            (fournisseurId != null ? "fournisseurId=" + fournisseurId + ", " : "") +
            (transporteurId != null ? "transporteurId=" + transporteurId + ", " : "") +
            (produitId != null ? "produitId=" + produitId + ", " : "") +
            "}";
    }

}
