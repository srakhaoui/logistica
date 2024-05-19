package com.logistica.domain;

import com.logistica.domain.enumeration.ModeReglement;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Entity
@Table(name = "reglement_espece")
@EntityListeners(AuditingEntityListener.class)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ReglementEspece {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Positive
    @Column(name = "montant", nullable = false)
    private double montant;

    @NotNull
    @Column(name = "date_reglement", nullable = false)
    private LocalDate dateReglement;

    @Embedded
    private Audit audit = new Audit();

    public ReglementEspece() {
        this.dateReglement= LocalDate.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Audit getAudit() {
        return audit;
    }

    public void setAudit(Audit audit) {
        this.audit = audit;
    }

    public static ReglementEspece of(double montant){
        ReglementEspece reglementEspece = new ReglementEspece();
        reglementEspece.setMontant(montant);
        return  reglementEspece;
    }
}
