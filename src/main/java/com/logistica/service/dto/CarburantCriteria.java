package com.logistica.service.dto;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the {@link com.logistica.domain.Carburant} entity. This class is used
 * in {@link com.logistica.web.rest.CarburantResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /carburants?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CarburantCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter code;

    private StringFilter categorie;

    public CarburantCriteria() {
    }

    public CarburantCriteria(CarburantCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.categorie = other.categorie == null ? null : other.categorie.copy();
    }

    @Override
    public CarburantCriteria copy() {
        return new CarburantCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCode() {
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
    }

    public StringFilter getCategorie() {
        return categorie;
    }

    public void setCategorie(StringFilter categorie) {
        this.categorie = categorie;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CarburantCriteria that = (CarburantCriteria) o;
        return
            Objects.equals(id, that.id) &&
                Objects.equals(code, that.code) &&
                Objects.equals(categorie, that.categorie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            code,
            categorie
        );
    }

    @Override
    public String toString() {
        return "CarburantCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (code != null ? "code=" + code + ", " : "") +
            (categorie != null ? "categorie=" + categorie + ", " : "") +
            "}";
    }

}
