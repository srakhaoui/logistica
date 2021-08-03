package com.logistica.service.dto;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the {@link com.logistica.domain.ClientGrossiste} entity. This class is used
 * in {@link com.logistica.web.rest.ClientGrossisteResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /client-grossistes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ClientGrossisteCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nom;

    private StringFilter adresse;

    private StringFilter telephone;

    public ClientGrossisteCriteria() {
    }

    public ClientGrossisteCriteria(ClientGrossisteCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nom = other.nom == null ? null : other.nom.copy();
        this.adresse = other.adresse == null ? null : other.adresse.copy();
        this.telephone = other.telephone == null ? null : other.telephone.copy();
    }

    @Override
    public ClientGrossisteCriteria copy() {
        return new ClientGrossisteCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNom() {
        return nom;
    }

    public void setNom(StringFilter nom) {
        this.nom = nom;
    }

    public StringFilter getAdresse() {
        return adresse;
    }

    public void setAdresse(StringFilter adresse) {
        this.adresse = adresse;
    }

    public StringFilter getTelephone() {
        return telephone;
    }

    public void setTelephone(StringFilter telephone) {
        this.telephone = telephone;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ClientGrossisteCriteria that = (ClientGrossisteCriteria) o;
        return
            Objects.equals(id, that.id) &&
                Objects.equals(nom, that.nom) &&
                Objects.equals(adresse, that.adresse) &&
                Objects.equals(telephone, that.telephone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            nom,
            adresse,
            telephone
        );
    }

    @Override
    public String toString() {
        return "ClientGrossisteCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (nom != null ? "nom=" + nom + ", " : "") +
            (adresse != null ? "adresse=" + adresse + ", " : "") +
            (telephone != null ? "telephone=" + telephone + ", " : "") +
            "}";
    }

}
