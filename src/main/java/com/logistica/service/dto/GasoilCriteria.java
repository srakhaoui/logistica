package com.logistica.service.dto;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the {@link com.logistica.domain.Gasoil} entity. This class is used
 * in {@link com.logistica.web.rest.GasoilResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /gasoils?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class GasoilCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter societe;

    private LongFilter numeroBonGasoil;

    private FloatFilter quantiteEnLitre;

    private FloatFilter prixDuLitre;

    private FloatFilter prixTotalGasoil;

    private IntegerFilter kilometrageInitial;

    private IntegerFilter kilometrageFinal;

    private IntegerFilter kilometrageParcouru;

    private LongFilter transporteurId;

    private LongFilter societeFacturationId;

    public GasoilCriteria() {
    }

    public GasoilCriteria(GasoilCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.societe = other.societe == null ? null : other.societe.copy();
        this.numeroBonGasoil = other.numeroBonGasoil == null ? null : other.numeroBonGasoil.copy();
        this.quantiteEnLitre = other.quantiteEnLitre == null ? null : other.quantiteEnLitre.copy();
        this.prixDuLitre = other.prixDuLitre == null ? null : other.prixDuLitre.copy();
        this.prixTotalGasoil = other.prixTotalGasoil == null ? null : other.prixTotalGasoil.copy();
        this.kilometrageInitial = other.kilometrageInitial == null ? null : other.kilometrageInitial.copy();
        this.kilometrageFinal = other.kilometrageFinal == null ? null : other.kilometrageFinal.copy();
        this.kilometrageParcouru = other.kilometrageParcouru == null ? null : other.kilometrageParcouru.copy();
        this.transporteurId = other.transporteurId == null ? null : other.transporteurId.copy();
        this.societeFacturationId = other.societeFacturationId == null ? null : other.societeFacturationId.copy();
    }

    @Override
    public GasoilCriteria copy() {
        return new GasoilCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getSociete() {
        return societe;
    }

    public void setSociete(StringFilter societe) {
        this.societe = societe;
    }

    public LongFilter getNumeroBonGasoil() {
        return numeroBonGasoil;
    }

    public void setNumeroBonGasoil(LongFilter numeroBonGasoil) {
        this.numeroBonGasoil = numeroBonGasoil;
    }

    public FloatFilter getQuantiteEnLitre() {
        return quantiteEnLitre;
    }

    public void setQuantiteEnLitre(FloatFilter quantiteEnLitre) {
        this.quantiteEnLitre = quantiteEnLitre;
    }

    public FloatFilter getPrixDuLitre() {
        return prixDuLitre;
    }

    public void setPrixDuLitre(FloatFilter prixDuLitre) {
        this.prixDuLitre = prixDuLitre;
    }

    public FloatFilter getPrixTotalGasoil() {
        return prixTotalGasoil;
    }

    public void setPrixTotalGasoil(FloatFilter prixTotalGasoil) {
        this.prixTotalGasoil = prixTotalGasoil;
    }

    public IntegerFilter getKilometrageInitial() {
        return kilometrageInitial;
    }

    public void setKilometrageInitial(IntegerFilter kilometrageInitial) {
        this.kilometrageInitial = kilometrageInitial;
    }

    public IntegerFilter getKilometrageFinal() {
        return kilometrageFinal;
    }

    public void setKilometrageFinal(IntegerFilter kilometrageFinal) {
        this.kilometrageFinal = kilometrageFinal;
    }

    public IntegerFilter getKilometrageParcouru() {
        return kilometrageParcouru;
    }

    public void setKilometrageParcouru(IntegerFilter kilometrageParcouru) {
        this.kilometrageParcouru = kilometrageParcouru;
    }

    public LongFilter getTransporteurId() {
        return transporteurId;
    }

    public void setTransporteurId(LongFilter transporteurId) {
        this.transporteurId = transporteurId;
    }

    public LongFilter getSocieteFacturationId() {
        return societeFacturationId;
    }

    public void setSocieteFacturationId(LongFilter societeFacturationId) {
        this.societeFacturationId = societeFacturationId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final GasoilCriteria that = (GasoilCriteria) o;
        return
            Objects.equals(id, that.id) &&
                Objects.equals(societe, that.societe) &&
                Objects.equals(numeroBonGasoil, that.numeroBonGasoil) &&
                Objects.equals(quantiteEnLitre, that.quantiteEnLitre) &&
                Objects.equals(prixDuLitre, that.prixDuLitre) &&
                Objects.equals(prixTotalGasoil, that.prixTotalGasoil) &&
                Objects.equals(kilometrageInitial, that.kilometrageInitial) &&
                Objects.equals(kilometrageFinal, that.kilometrageFinal) &&
                Objects.equals(kilometrageParcouru, that.kilometrageParcouru) &&
                Objects.equals(transporteurId, that.transporteurId) &&
                Objects.equals(societeFacturationId, that.societeFacturationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            societe,
            numeroBonGasoil,
            quantiteEnLitre,
            prixDuLitre,
            prixTotalGasoil,
            kilometrageInitial,
            kilometrageFinal,
            kilometrageParcouru,
            transporteurId,
            societeFacturationId
        );
    }

    @Override
    public String toString() {
        return "GasoilCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (societe != null ? "societe=" + societe + ", " : "") +
            (numeroBonGasoil != null ? "numeroBonGasoil=" + numeroBonGasoil + ", " : "") +
            (quantiteEnLitre != null ? "quantiteEnLitre=" + quantiteEnLitre + ", " : "") +
            (prixDuLitre != null ? "prixDuLitre=" + prixDuLitre + ", " : "") +
            (prixTotalGasoil != null ? "prixTotalGasoil=" + prixTotalGasoil + ", " : "") +
            (kilometrageInitial != null ? "kilometrageInitial=" + kilometrageInitial + ", " : "") +
            (kilometrageFinal != null ? "kilometrageFinal=" + kilometrageFinal + ", " : "") +
            (kilometrageParcouru != null ? "kilometrageParcouru=" + kilometrageParcouru + ", " : "") +
            (transporteurId != null ? "transporteurId=" + transporteurId + ", " : "") +
            (societeFacturationId != null ? "societeFacturationId=" + societeFacturationId + ", " : "") +
            "}";
    }

}
