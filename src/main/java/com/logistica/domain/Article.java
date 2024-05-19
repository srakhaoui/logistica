package com.logistica.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
@Table(name = "article")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Article {

    public static final String ENTITY_NAME = "Article";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull
    @Column(name = "designation", nullable = false)
    private String designation;

    @NotNull
    @Positive
    @Column(name = "quantite", nullable = false)
    private double quantite;

    @NotNull
    @Positive
    @Column(name = "prix_unitaire_ht", nullable = false)
    private double prixUnitaireHt;

    @NotNull
    @Positive
    @Column(name = "montant", nullable = false)
    private double montant;

    @NotNull
    @Column(name="facture_id", nullable = false)
    private Long factureId;

    public static Article of(String code, String designation, Double quantite, Double montant) {
        Article article = new Article();
        article.setCode(code);
        article.setDesignation(designation);
        article.setQuantite(quantite);
        article.setPrixUnitaireHt(montant / quantite);
        article.setMontant(montant);
        return article;
    }

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public double getQuantite() {
        return quantite;
    }

    public void setQuantite(double quantite) {
        this.quantite = quantite;
    }

    public double getPrixUnitaireHt() {
        return prixUnitaireHt;
    }

    public void setPrixUnitaireHt(double prixUnitaireHt) {
        this.prixUnitaireHt = prixUnitaireHt;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }
}
