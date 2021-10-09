package com.logistica.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.logistica.domain.enumeration.Platform;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * A Gasoil.
 */
@Entity
@Table(name = "gasoil")
@EntityListeners(AuditingEntityListener.class)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Gasoil implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gasoilSequenceGenerator")
    @SequenceGenerator(name = "gasoilSequenceGenerator", sequenceName = "gasoil-seq", allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "numero_bon_gasoil", nullable = false)
    private Long numeroBonGasoil;

    @NotNull
    @Column(name = "quantite_en_litre", nullable = false)
    private Float quantiteEnLitre;

    @NotNull
    @Column(name = "prix_du_litre", nullable = false)
    private Float prixDuLitre;

    @Column(name = "prix_total_gasoil")
    private Float prixTotalGasoil;

    @Column(name = "kilometrage_initial")
    private Integer kilometrageInitial;

    @Column(name = "kilometrage_final")
    private Integer kilometrageFinal;

    @Column(name = "kilometrage_parcouru")
    private Integer kilometrageParcouru;

    @ManyToOne
    @JsonIgnoreProperties("gasoils")
    private Transporteur transporteur;

    @ManyToOne
    @JsonIgnoreProperties("gasoils")
    private Societe societeFacturation;

    @Column(name = "date_saisie", nullable = false)
    private LocalDate dateSaisie = LocalDate.now();

    @Column(name = "date_bon_gasoil", nullable = false)
    private LocalDateTime dateBonGasoil;

    @Enumerated(EnumType.STRING)
    @Column(name = "platform", nullable = false)
    private Platform platform;

    @Embedded
    private Audit audit = new Audit();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumeroBonGasoil() {
        return numeroBonGasoil;
    }

    public Gasoil numeroBonGasoil(Long numeroBonGasoil) {
        this.numeroBonGasoil = numeroBonGasoil;
        return this;
    }

    public void setNumeroBonGasoil(Long numeroBonGasoil) {
        this.numeroBonGasoil = numeroBonGasoil;
    }

    public Float getQuantiteEnLitre() {
        return quantiteEnLitre;
    }

    public Gasoil quantiteEnLitre(Float quantiteEnLitre) {
        this.quantiteEnLitre = quantiteEnLitre;
        return this;
    }

    public void setQuantiteEnLitre(Float quantiteEnLitre) {
        this.quantiteEnLitre = quantiteEnLitre;
    }

    public Float getPrixDuLitre() {
        return prixDuLitre;
    }

    public Gasoil prixDuLitre(Float prixDuLitre) {
        this.prixDuLitre = prixDuLitre;
        return this;
    }

    public void setPrixDuLitre(Float prixDuLitre) {
        this.prixDuLitre = prixDuLitre;
    }

    public Float getPrixTotalGasoil() {
        return prixTotalGasoil;
    }

    public Gasoil prixTotalGasoil(Float prixTotalGasoil) {
        this.prixTotalGasoil = prixTotalGasoil;
        return this;
    }

    public void setPrixTotalGasoil(Float prixTotalGasoil) {
        this.prixTotalGasoil = prixTotalGasoil;
    }

    public Integer getKilometrageInitial() {
        return kilometrageInitial;
    }

    public Gasoil kilometrageInitial(Integer kilometrageInitial) {
        this.kilometrageInitial = kilometrageInitial;
        return this;
    }

    public void setKilometrageInitial(Integer kilometrageInitial) {
        this.kilometrageInitial = kilometrageInitial;
    }

    public Integer getKilometrageFinal() {
        return kilometrageFinal;
    }

    public Gasoil kilometrageFinal(Integer kilometrageFinal) {
        this.kilometrageFinal = kilometrageFinal;
        return this;
    }

    public void setKilometrageFinal(Integer kilometrageFinal) {
        this.kilometrageFinal = kilometrageFinal;
    }

    public Integer getKilometrageParcouru() {
        return kilometrageParcouru;
    }

    public Gasoil kilometrageParcouru(Integer kilometrageParcouru) {
        this.kilometrageParcouru = kilometrageParcouru;
        return this;
    }

    public void setKilometrageParcouru(Integer kilometrageParcouru) {
        this.kilometrageParcouru = kilometrageParcouru;
    }

    public Transporteur getTransporteur() {
        return transporteur;
    }

    public Gasoil transporteur(Transporteur transporteur) {
        this.transporteur = transporteur;
        return this;
    }

    public void setTransporteur(Transporteur transporteur) {
        this.transporteur = transporteur;
    }

    public Societe getSocieteFacturation() {
        return societeFacturation;
    }

    public Gasoil societeFacturation(Societe societe) {
        this.societeFacturation = societe;
        return this;
    }

    public void setSocieteFacturation(Societe societe) {
        this.societeFacturation = societe;
    }

    public LocalDate getDateSaisie() {
        return dateSaisie;
    }

    public void setDateSaisie(LocalDate dateSaisie) {
        this.dateSaisie = dateSaisie;
    }

    public LocalDateTime getDateBonGasoil() {
        return dateBonGasoil;
    }

    public void setDateBonGasoil(LocalDateTime dateBonGasoil) {
        this.dateBonGasoil = dateBonGasoil;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public Audit getAudit() {
        return audit;
    }

    public void setAudit(Audit audit) {
        this.audit = audit;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Gasoil)) {
            return false;
        }
        return id != null && id.equals(((Gasoil) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Gasoil{" +
            "id=" + getId() +
            ", numeroBonGasoil=" + getNumeroBonGasoil() +
            ", quantiteEnLitre=" + getQuantiteEnLitre() +
            ", prixDuLitre=" + getPrixDuLitre() +
            ", prixTotalGasoil=" + getPrixTotalGasoil() +
            ", kilometrageInitial=" + getKilometrageInitial() +
            ", kilometrageFinal=" + getKilometrageFinal() +
            ", kilometrageParcouru=" + getKilometrageParcouru() +
            "}";
    }
}
