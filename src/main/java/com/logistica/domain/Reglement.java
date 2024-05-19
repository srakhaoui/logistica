package com.logistica.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.logistica.domain.enumeration.ModeReglement;
import com.logistica.web.rest.errors.BadRequestAlertException;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

import static com.logistica.domain.Facture.ENTITY_NAME;

@Entity
@Table(name = "reglement")
@EntityListeners(AuditingEntityListener.class)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Reglement {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name="facture_id", nullable = false)
    private Long factureId;

    @Transient
    @JsonIgnore
    private double montantFacture;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "mode_reglement", nullable = false)
    private ModeReglement modeReglement;

    @Positive
    @Column(name = "montant", nullable = false)
    private double montant;

    @NotNull
    @Column(name = "date_reglement", nullable = false)
    private LocalDate dateReglement = LocalDate.now();

    @Column(name = "reference", nullable = false)
    private String reference;

    @Embedded
    private Audit audit = new Audit();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFactureId() {
        return factureId;
    }

    public void setFactureId(Long factureId) {
        this.factureId = factureId;
    }

    public double getMontantFacture() {
        return montantFacture;
    }

    public void setMontantFacture(double montantFacture) {
        this.montantFacture = montantFacture;
    }

    public ModeReglement getModeReglement() {
        return modeReglement;
    }

    public void setModeReglement(ModeReglement modeReglement) {
        this.modeReglement = modeReglement;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public LocalDate getDateReglement() {
        return dateReglement;
    }

    public void setDateReglement(LocalDate dateReglement) {
        this.dateReglement = dateReglement;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Audit getAudit() {
        return audit;
    }

    public void setAudit(Audit audit) {
        this.audit = audit;
    }

    public static Reglement of(Facture facture){
        Reglement reglement = new Reglement();
        reglement.setFactureId(facture.getId());
        reglement.setMontantFacture(facture.getTotalPrixHt());
        return reglement;
    }
    public Reglement regler(double montant, ModeReglement modeReglement, String reference){
        Assert.notNull(factureId, "no invoice is set");
        if(montant > montantFacture){
            throw new BadRequestAlertException("The payment amount could not exceed the invoice's one", ENTITY_NAME, "reglement.montant.invalid");
        }
        setMontant(montant);
        setModeReglement(modeReglement);
        setReference(reference);
        return  this;
    }
}
