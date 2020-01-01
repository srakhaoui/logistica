package com.logistica.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.logistica.domain.Trajet} entity. This class is used
 * in {@link com.logistica.web.rest.TrajetResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /trajets?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TrajetCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter depart;

    private StringFilter destination;

    private StringFilter description;

    private FloatFilter commission;

    public TrajetCriteria(){
    }

    public TrajetCriteria(TrajetCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.depart = other.depart == null ? null : other.depart.copy();
        this.destination = other.destination == null ? null : other.destination.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.commission = other.commission == null ? null : other.commission.copy();
    }

    @Override
    public TrajetCriteria copy() {
        return new TrajetCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDepart() {
        return depart;
    }

    public void setDepart(StringFilter depart) {
        this.depart = depart;
    }

    public StringFilter getDestination() {
        return destination;
    }

    public void setDestination(StringFilter destination) {
        this.destination = destination;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public FloatFilter getCommission() {
        return commission;
    }

    public void setCommission(FloatFilter commission) {
        this.commission = commission;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TrajetCriteria that = (TrajetCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(depart, that.depart) &&
            Objects.equals(destination, that.destination) &&
            Objects.equals(description, that.description) &&
            Objects.equals(commission, that.commission);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        depart,
        destination,
        description,
        commission
        );
    }

    @Override
    public String toString() {
        return "TrajetCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (depart != null ? "depart=" + depart + ", " : "") +
                (destination != null ? "destination=" + destination + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (commission != null ? "commission=" + commission + ", " : "") +
            "}";
    }

}
