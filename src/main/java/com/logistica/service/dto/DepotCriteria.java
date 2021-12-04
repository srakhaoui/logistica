package com.logistica.service.dto;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the {@link com.logistica.domain.Depot} entity. This class is used
 * in {@link com.logistica.web.rest.DepotResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /depots?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DepotCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private FloatFilter stock;

    private StringFilter nom;

    private BooleanFilter consommationInterne;

    private LongFilter alimentationId;

    private LongFilter consommationId;

    public DepotCriteria() {
    }

    public DepotCriteria(DepotCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.stock = other.stock == null ? null : other.stock.copy();
        this.nom = other.nom == null ? null : other.nom.copy();
        this.consommationInterne = other.consommationInterne == null ? null : other.consommationInterne.copy();
        this.alimentationId = other.alimentationId == null ? null : other.alimentationId.copy();
        this.consommationId = other.consommationId == null ? null : other.consommationId.copy();
    }

    @Override
    public DepotCriteria copy() {
        return new DepotCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public FloatFilter getStock() {
        return stock;
    }

    public void setStock(FloatFilter stock) {
        this.stock = stock;
    }

    public StringFilter getNom() {
        return nom;
    }

    public void setNom(StringFilter nom) {
        this.nom = nom;
    }

    public BooleanFilter getConsommationInterne() {
        return consommationInterne;
    }

    public void setConsommationInterne(BooleanFilter consommationInterne) {
        this.consommationInterne = consommationInterne;
    }

    public LongFilter getAlimentationId() {
        return alimentationId;
    }

    public void setAlimentationId(LongFilter alimentationId) {
        this.alimentationId = alimentationId;
    }

    public LongFilter getConsommationId() {
        return consommationId;
    }

    public void setConsommationId(LongFilter consommationId) {
        this.consommationId = consommationId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DepotCriteria that = (DepotCriteria) o;
        return
            Objects.equals(id, that.id) &&
                Objects.equals(stock, that.stock) &&
                Objects.equals(nom, that.nom) &&
                Objects.equals(consommationInterne, that.consommationInterne) &&
                Objects.equals(alimentationId, that.alimentationId) &&
                Objects.equals(consommationId, that.consommationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            stock,
            nom,
            consommationInterne,
            alimentationId,
            consommationId
        );
    }

    @Override
    public String toString() {
        return "DepotCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (stock != null ? "stock=" + stock + ", " : "") +
            (nom != null ? "nom=" + nom + ", " : "") +
            (consommationInterne != null ? "consommationInterne=" + consommationInterne + ", " : "") +
            (alimentationId != null ? "alimentationId=" + alimentationId + ", " : "") +
            (consommationId != null ? "consommationId=" + consommationId + ", " : "") +
            "}";
    }

}
