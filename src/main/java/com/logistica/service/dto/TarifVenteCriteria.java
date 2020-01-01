package com.logistica.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.logistica.domain.enumeration.Unite;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.logistica.domain.TarifVente} entity. This class is used
 * in {@link com.logistica.web.rest.TarifVenteResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /tarif-ventes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TarifVenteCriteria implements Serializable, Criteria {
    /**
     * Class for filtering Unite
     */
    public static class UniteFilter extends Filter<Unite> {

        public UniteFilter() {
        }

        public UniteFilter(UniteFilter filter) {
            super(filter);
        }

        @Override
        public UniteFilter copy() {
            return new UniteFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private UniteFilter unite;

    private FloatFilter prix;

    private LongFilter clientId;

    private LongFilter produitId;

    public TarifVenteCriteria(){
    }

    public TarifVenteCriteria(TarifVenteCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.unite = other.unite == null ? null : other.unite.copy();
        this.prix = other.prix == null ? null : other.prix.copy();
        this.clientId = other.clientId == null ? null : other.clientId.copy();
        this.produitId = other.produitId == null ? null : other.produitId.copy();
    }

    @Override
    public TarifVenteCriteria copy() {
        return new TarifVenteCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public UniteFilter getUnite() {
        return unite;
    }

    public void setUnite(UniteFilter unite) {
        this.unite = unite;
    }

    public FloatFilter getPrix() {
        return prix;
    }

    public void setPrix(FloatFilter prix) {
        this.prix = prix;
    }

    public LongFilter getClientId() {
        return clientId;
    }

    public void setClientId(LongFilter clientId) {
        this.clientId = clientId;
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
        final TarifVenteCriteria that = (TarifVenteCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(unite, that.unite) &&
            Objects.equals(prix, that.prix) &&
            Objects.equals(clientId, that.clientId) &&
            Objects.equals(produitId, that.produitId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        unite,
        prix,
        clientId,
        produitId
        );
    }

    @Override
    public String toString() {
        return "TarifVenteCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (unite != null ? "unite=" + unite + ", " : "") +
                (prix != null ? "prix=" + prix + ", " : "") +
                (clientId != null ? "clientId=" + clientId + ", " : "") +
                (produitId != null ? "produitId=" + produitId + ", " : "") +
            "}";
    }

}
