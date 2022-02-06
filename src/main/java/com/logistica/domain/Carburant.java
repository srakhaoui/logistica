package com.logistica.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * A Carburant.
 */
@Entity
@Table(name = "carburant")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Carburant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "carburantSequenceGenerator")
    @SequenceGenerator(name = "carburantSequenceGenerator", sequenceName = "carburant-seq", allocationSize = 1)
    private Long id;

    @NotNull
    @Size(min = 1)
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "categorie")
    private String categorie;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public Carburant code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCategorie() {
        return categorie;
    }

    public Carburant categorie(String categorie) {
        this.categorie = categorie;
        return this;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Carburant)) {
            return false;
        }
        return id != null && id.equals(((Carburant) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Carburant{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", categorie='" + getCategorie() + "'" +
            "}";
    }
}
