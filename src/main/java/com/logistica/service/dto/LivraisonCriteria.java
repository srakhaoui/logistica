package com.logistica.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.logistica.domain.enumeration.Unite;
import com.logistica.domain.enumeration.Unite;
import com.logistica.domain.enumeration.TypeLivraison;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.logistica.domain.Livraison} entity. This class is used
 * in {@link com.logistica.web.rest.LivraisonResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /livraisons?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class LivraisonCriteria implements Serializable, Criteria {
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
    /**
     * Class for filtering TypeLivraison
     */
    public static class TypeLivraisonFilter extends Filter<TypeLivraison> {

        public TypeLivraisonFilter() {
        }

        public TypeLivraisonFilter(TypeLivraisonFilter filter) {
            super(filter);
        }

        @Override
        public TypeLivraisonFilter copy() {
            return new TypeLivraisonFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter dateBonCommande;

    private IntegerFilter numeroBonCommande;

    private IntegerFilter numeroBonLivraison;

    private LocalDateFilter dateBonLivraison;

    private IntegerFilter numeroBonFournisseur;

    private FloatFilter quantiteVendue;

    private UniteFilter uniteVente;

    private FloatFilter prixTotalVente;

    private FloatFilter quantiteAchetee;

    private UniteFilter uniteAchat;

    private FloatFilter prixTotalAchat;

    private FloatFilter quantiteConvertie;

    private TypeLivraisonFilter type;

    private BooleanFilter facture;

    private LocalDateFilter dateBonCaisse;

    private FloatFilter reparationDivers;

    private FloatFilter trax;

    private FloatFilter balance;

    private FloatFilter avance;

    private FloatFilter autoroute;

    private FloatFilter dernierEtat;

    private FloatFilter penaliteEse;

    private FloatFilter penaliteChfrs;

    private FloatFilter fraisEspece;

    private FloatFilter retenu;

    private FloatFilter totalComission;

    private LongFilter fournisseurId;

    private LongFilter clientId;

    private LongFilter transporteurId;

    private LongFilter trajetId;

    private LongFilter produitId;

    private LongFilter societeFacturationId;

    public LivraisonCriteria(){
    }

    public LivraisonCriteria(LivraisonCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.dateBonCommande = other.dateBonCommande == null ? null : other.dateBonCommande.copy();
        this.numeroBonCommande = other.numeroBonCommande == null ? null : other.numeroBonCommande.copy();
        this.numeroBonLivraison = other.numeroBonLivraison == null ? null : other.numeroBonLivraison.copy();
        this.dateBonLivraison = other.dateBonLivraison == null ? null : other.dateBonLivraison.copy();
        this.numeroBonFournisseur = other.numeroBonFournisseur == null ? null : other.numeroBonFournisseur.copy();
        this.quantiteVendue = other.quantiteVendue == null ? null : other.quantiteVendue.copy();
        this.uniteVente = other.uniteVente == null ? null : other.uniteVente.copy();
        this.prixTotalVente = other.prixTotalVente == null ? null : other.prixTotalVente.copy();
        this.quantiteAchetee = other.quantiteAchetee == null ? null : other.quantiteAchetee.copy();
        this.uniteAchat = other.uniteAchat == null ? null : other.uniteAchat.copy();
        this.prixTotalAchat = other.prixTotalAchat == null ? null : other.prixTotalAchat.copy();
        this.quantiteConvertie = other.quantiteConvertie == null ? null : other.quantiteConvertie.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.facture = other.facture == null ? null : other.facture.copy();
        this.dateBonCaisse = other.dateBonCaisse == null ? null : other.dateBonCaisse.copy();
        this.reparationDivers = other.reparationDivers == null ? null : other.reparationDivers.copy();
        this.trax = other.trax == null ? null : other.trax.copy();
        this.balance = other.balance == null ? null : other.balance.copy();
        this.avance = other.avance == null ? null : other.avance.copy();
        this.autoroute = other.autoroute == null ? null : other.autoroute.copy();
        this.dernierEtat = other.dernierEtat == null ? null : other.dernierEtat.copy();
        this.penaliteEse = other.penaliteEse == null ? null : other.penaliteEse.copy();
        this.penaliteChfrs = other.penaliteChfrs == null ? null : other.penaliteChfrs.copy();
        this.fraisEspece = other.fraisEspece == null ? null : other.fraisEspece.copy();
        this.retenu = other.retenu == null ? null : other.retenu.copy();
        this.totalComission = other.totalComission == null ? null : other.totalComission.copy();
        this.fournisseurId = other.fournisseurId == null ? null : other.fournisseurId.copy();
        this.clientId = other.clientId == null ? null : other.clientId.copy();
        this.transporteurId = other.transporteurId == null ? null : other.transporteurId.copy();
        this.trajetId = other.trajetId == null ? null : other.trajetId.copy();
        this.produitId = other.produitId == null ? null : other.produitId.copy();
        this.societeFacturationId = other.societeFacturationId == null ? null : other.societeFacturationId.copy();
    }

    @Override
    public LivraisonCriteria copy() {
        return new LivraisonCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getDateBonCommande() {
        return dateBonCommande;
    }

    public void setDateBonCommande(LocalDateFilter dateBonCommande) {
        this.dateBonCommande = dateBonCommande;
    }

    public IntegerFilter getNumeroBonCommande() {
        return numeroBonCommande;
    }

    public void setNumeroBonCommande(IntegerFilter numeroBonCommande) {
        this.numeroBonCommande = numeroBonCommande;
    }

    public IntegerFilter getNumeroBonLivraison() {
        return numeroBonLivraison;
    }

    public void setNumeroBonLivraison(IntegerFilter numeroBonLivraison) {
        this.numeroBonLivraison = numeroBonLivraison;
    }

    public LocalDateFilter getDateBonLivraison() {
        return dateBonLivraison;
    }

    public void setDateBonLivraison(LocalDateFilter dateBonLivraison) {
        this.dateBonLivraison = dateBonLivraison;
    }

    public IntegerFilter getNumeroBonFournisseur() {
        return numeroBonFournisseur;
    }

    public void setNumeroBonFournisseur(IntegerFilter numeroBonFournisseur) {
        this.numeroBonFournisseur = numeroBonFournisseur;
    }

    public FloatFilter getQuantiteVendue() {
        return quantiteVendue;
    }

    public void setQuantiteVendue(FloatFilter quantiteVendue) {
        this.quantiteVendue = quantiteVendue;
    }

    public UniteFilter getUniteVente() {
        return uniteVente;
    }

    public void setUniteVente(UniteFilter uniteVente) {
        this.uniteVente = uniteVente;
    }

    public FloatFilter getPrixTotalVente() {
        return prixTotalVente;
    }

    public void setPrixTotalVente(FloatFilter prixTotalVente) {
        this.prixTotalVente = prixTotalVente;
    }

    public FloatFilter getQuantiteAchetee() {
        return quantiteAchetee;
    }

    public void setQuantiteAchetee(FloatFilter quantiteAchetee) {
        this.quantiteAchetee = quantiteAchetee;
    }

    public UniteFilter getUniteAchat() {
        return uniteAchat;
    }

    public void setUniteAchat(UniteFilter uniteAchat) {
        this.uniteAchat = uniteAchat;
    }

    public FloatFilter getPrixTotalAchat() {
        return prixTotalAchat;
    }

    public void setPrixTotalAchat(FloatFilter prixTotalAchat) {
        this.prixTotalAchat = prixTotalAchat;
    }

    public FloatFilter getQuantiteConvertie() {
        return quantiteConvertie;
    }

    public void setQuantiteConvertie(FloatFilter quantiteConvertie) {
        this.quantiteConvertie = quantiteConvertie;
    }

    public TypeLivraisonFilter getType() {
        return type;
    }

    public void setType(TypeLivraisonFilter type) {
        this.type = type;
    }

    public BooleanFilter getFacture() {
        return facture;
    }

    public void setFacture(BooleanFilter facture) {
        this.facture = facture;
    }

    public LocalDateFilter getDateBonCaisse() {
        return dateBonCaisse;
    }

    public void setDateBonCaisse(LocalDateFilter dateBonCaisse) {
        this.dateBonCaisse = dateBonCaisse;
    }

    public FloatFilter getReparationDivers() {
        return reparationDivers;
    }

    public void setReparationDivers(FloatFilter reparationDivers) {
        this.reparationDivers = reparationDivers;
    }

    public FloatFilter getTrax() {
        return trax;
    }

    public void setTrax(FloatFilter trax) {
        this.trax = trax;
    }

    public FloatFilter getBalance() {
        return balance;
    }

    public void setBalance(FloatFilter balance) {
        this.balance = balance;
    }

    public FloatFilter getAvance() {
        return avance;
    }

    public void setAvance(FloatFilter avance) {
        this.avance = avance;
    }

    public FloatFilter getAutoroute() {
        return autoroute;
    }

    public void setAutoroute(FloatFilter autoroute) {
        this.autoroute = autoroute;
    }

    public FloatFilter getDernierEtat() {
        return dernierEtat;
    }

    public void setDernierEtat(FloatFilter dernierEtat) {
        this.dernierEtat = dernierEtat;
    }

    public FloatFilter getPenaliteEse() {
        return penaliteEse;
    }

    public void setPenaliteEse(FloatFilter penaliteEse) {
        this.penaliteEse = penaliteEse;
    }

    public FloatFilter getPenaliteChfrs() {
        return penaliteChfrs;
    }

    public void setPenaliteChfrs(FloatFilter penaliteChfrs) {
        this.penaliteChfrs = penaliteChfrs;
    }

    public FloatFilter getFraisEspece() {
        return fraisEspece;
    }

    public void setFraisEspece(FloatFilter fraisEspece) {
        this.fraisEspece = fraisEspece;
    }

    public FloatFilter getRetenu() {
        return retenu;
    }

    public void setRetenu(FloatFilter retenu) {
        this.retenu = retenu;
    }

    public FloatFilter getTotalComission() {
        return totalComission;
    }

    public void setTotalComission(FloatFilter totalComission) {
        this.totalComission = totalComission;
    }

    public LongFilter getFournisseurId() {
        return fournisseurId;
    }

    public void setFournisseurId(LongFilter fournisseurId) {
        this.fournisseurId = fournisseurId;
    }

    public LongFilter getClientId() {
        return clientId;
    }

    public void setClientId(LongFilter clientId) {
        this.clientId = clientId;
    }

    public LongFilter getTransporteurId() {
        return transporteurId;
    }

    public void setTransporteurId(LongFilter transporteurId) {
        this.transporteurId = transporteurId;
    }

    public LongFilter getTrajetId() {
        return trajetId;
    }

    public void setTrajetId(LongFilter trajetId) {
        this.trajetId = trajetId;
    }

    public LongFilter getProduitId() {
        return produitId;
    }

    public void setProduitId(LongFilter produitId) {
        this.produitId = produitId;
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
        final LivraisonCriteria that = (LivraisonCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(dateBonCommande, that.dateBonCommande) &&
            Objects.equals(numeroBonCommande, that.numeroBonCommande) &&
            Objects.equals(numeroBonLivraison, that.numeroBonLivraison) &&
            Objects.equals(dateBonLivraison, that.dateBonLivraison) &&
            Objects.equals(numeroBonFournisseur, that.numeroBonFournisseur) &&
            Objects.equals(quantiteVendue, that.quantiteVendue) &&
            Objects.equals(uniteVente, that.uniteVente) &&
            Objects.equals(prixTotalVente, that.prixTotalVente) &&
            Objects.equals(quantiteAchetee, that.quantiteAchetee) &&
            Objects.equals(uniteAchat, that.uniteAchat) &&
            Objects.equals(prixTotalAchat, that.prixTotalAchat) &&
            Objects.equals(quantiteConvertie, that.quantiteConvertie) &&
            Objects.equals(type, that.type) &&
            Objects.equals(facture, that.facture) &&
            Objects.equals(dateBonCaisse, that.dateBonCaisse) &&
            Objects.equals(reparationDivers, that.reparationDivers) &&
            Objects.equals(trax, that.trax) &&
            Objects.equals(balance, that.balance) &&
            Objects.equals(avance, that.avance) &&
            Objects.equals(autoroute, that.autoroute) &&
            Objects.equals(dernierEtat, that.dernierEtat) &&
            Objects.equals(penaliteEse, that.penaliteEse) &&
            Objects.equals(penaliteChfrs, that.penaliteChfrs) &&
            Objects.equals(fraisEspece, that.fraisEspece) &&
            Objects.equals(retenu, that.retenu) &&
            Objects.equals(totalComission, that.totalComission) &&
            Objects.equals(fournisseurId, that.fournisseurId) &&
            Objects.equals(clientId, that.clientId) &&
            Objects.equals(transporteurId, that.transporteurId) &&
            Objects.equals(trajetId, that.trajetId) &&
            Objects.equals(produitId, that.produitId) &&
            Objects.equals(societeFacturationId, that.societeFacturationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        dateBonCommande,
        numeroBonCommande,
        numeroBonLivraison,
        dateBonLivraison,
        numeroBonFournisseur,
        quantiteVendue,
        uniteVente,
        prixTotalVente,
        quantiteAchetee,
        uniteAchat,
        prixTotalAchat,
        quantiteConvertie,
        type,
        facture,
        dateBonCaisse,
        reparationDivers,
        trax,
        balance,
        avance,
        autoroute,
        dernierEtat,
        penaliteEse,
        penaliteChfrs,
        fraisEspece,
        retenu,
        totalComission,
        fournisseurId,
        clientId,
        transporteurId,
        trajetId,
        produitId,
        societeFacturationId
        );
    }

    @Override
    public String toString() {
        return "LivraisonCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (dateBonCommande != null ? "dateBonCommande=" + dateBonCommande + ", " : "") +
                (numeroBonCommande != null ? "numeroBonCommande=" + numeroBonCommande + ", " : "") +
                (numeroBonLivraison != null ? "numeroBonLivraison=" + numeroBonLivraison + ", " : "") +
                (dateBonLivraison != null ? "dateBonLivraison=" + dateBonLivraison + ", " : "") +
                (numeroBonFournisseur != null ? "numeroBonFournisseur=" + numeroBonFournisseur + ", " : "") +
                (quantiteVendue != null ? "quantiteVendue=" + quantiteVendue + ", " : "") +
                (uniteVente != null ? "uniteVente=" + uniteVente + ", " : "") +
                (prixTotalVente != null ? "prixTotalVente=" + prixTotalVente + ", " : "") +
                (quantiteAchetee != null ? "quantiteAchetee=" + quantiteAchetee + ", " : "") +
                (uniteAchat != null ? "uniteAchat=" + uniteAchat + ", " : "") +
                (prixTotalAchat != null ? "prixTotalAchat=" + prixTotalAchat + ", " : "") +
                (quantiteConvertie != null ? "quantiteConvertie=" + quantiteConvertie + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (facture != null ? "facture=" + facture + ", " : "") +
                (dateBonCaisse != null ? "dateBonCaisse=" + dateBonCaisse + ", " : "") +
                (reparationDivers != null ? "reparationDivers=" + reparationDivers + ", " : "") +
                (trax != null ? "trax=" + trax + ", " : "") +
                (balance != null ? "balance=" + balance + ", " : "") +
                (avance != null ? "avance=" + avance + ", " : "") +
                (autoroute != null ? "autoroute=" + autoroute + ", " : "") +
                (dernierEtat != null ? "dernierEtat=" + dernierEtat + ", " : "") +
                (penaliteEse != null ? "penaliteEse=" + penaliteEse + ", " : "") +
                (penaliteChfrs != null ? "penaliteChfrs=" + penaliteChfrs + ", " : "") +
                (fraisEspece != null ? "fraisEspece=" + fraisEspece + ", " : "") +
                (retenu != null ? "retenu=" + retenu + ", " : "") +
                (totalComission != null ? "totalComission=" + totalComission + ", " : "") +
                (fournisseurId != null ? "fournisseurId=" + fournisseurId + ", " : "") +
                (clientId != null ? "clientId=" + clientId + ", " : "") +
                (transporteurId != null ? "transporteurId=" + transporteurId + ", " : "") +
                (trajetId != null ? "trajetId=" + trajetId + ", " : "") +
                (produitId != null ? "produitId=" + produitId + ", " : "") +
                (societeFacturationId != null ? "societeFacturationId=" + societeFacturationId + ", " : "") +
            "}";
    }

}
