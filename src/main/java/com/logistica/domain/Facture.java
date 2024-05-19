package com.logistica.domain;

import com.logistica.domain.enumeration.InvoiceStatus;
import com.logistica.service.dto.RecapitulatifFacturationClient;
import com.logistica.web.rest.errors.BadRequestAlertException;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "facture")
@EntityListeners(AuditingEntityListener.class)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Facture {

    public static final String ENTITY_NAME = "Facture";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @ManyToOne
    private Client client;

    @NotNull
    @Column(name="date_facturation")
    private LocalDate dateFacturation;

    @Positive
    @Column(name="mois")
    private Integer mois;

    @Positive
    @Column(name="annee")
    private Integer annee;

    @Positive
    @Column(name="nombre_bons_livraison")
    private long nombreBonsLivraison;

    @Positive
    @Column(name="total_quantite")
    private double totalQuantite;

    @Positive
    @Column(name="total_prix_ht")
    private double totalPrixHt;

    @Positive
    @Column(name="total_prix_ttc")
    private double totalPrixTtc;

    @Positive
    @Column(name="tva")
    private Float tva;

    @PositiveOrZero
    @Column(name="remise")
    private Float remise;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TypeFacture type = TypeFacture.AUTO;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private InvoiceStatus status;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "factureId")
    private Set<Article> articles = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "factureId")
    private Set<Reglement> reglements = new HashSet<>();

    @Embedded
    private Audit audit = new Audit();

    public Set<Article> getArticles() {
        return articles;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }

    public Audit getAudit() {
        return audit;
    }

    public Facture(){
        dateFacturation = LocalDate.now();
        this.status = InvoiceStatus.INVALID;
    }

    public void setAudit(Audit audit) {
        this.audit = audit;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public LocalDate getDateFacturation() {
        return dateFacturation;
    }

    public void setDateFacturation(LocalDate dateFacturation) {
        this.dateFacturation = dateFacturation;
    }

    public Integer getMois() {
        return mois;
    }

    public void setMois(Integer mois) {
        this.mois = mois;
    }

    public Integer getAnnee() {
        return annee;
    }

    public void setAnnee(Integer annee) {
        this.annee = annee;
    }

    public long getNombreBonsLivraison() {
        return nombreBonsLivraison;
    }

    public void setNombreBonsLivraison(long nombreBonsLivraison) {
        this.nombreBonsLivraison = nombreBonsLivraison;
    }

    public double getTotalQuantite() {
        return totalQuantite;
    }

    public void setTotalQuantite(double totalQuantite) {
        this.totalQuantite = totalQuantite;
    }

    public double getTotalPrixHt() {
        return totalPrixHt;
    }

    public void setTotalPrixHt(double totalPrixHt) {
        this.totalPrixHt = totalPrixHt;
    }

    public double getTotalPrixTtc() {
        return totalPrixTtc;
    }

    public void setTotalPrixTtc(double totalPrixTtc) {
        this.totalPrixTtc = totalPrixTtc;
    }

    public Float getTva() {
        return tva;
    }

    public void setTva(Float tva) {
        this.tva = tva;
    }

    public TypeFacture getType() {
        return type;
    }

    public void setType(TypeFacture type) {
        this.type = type;
    }

    public InvoiceStatus getStatus() {
        return status;
    }

    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }

    public Float getRemise() {
        return remise;
    }

    public void setRemise(Float remise) {
        this.remise = remise;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Reglement> getReglements() {
        return reglements;
    }

    public void setReglements(Set<Reglement> reglements) {
        this.reglements = reglements;
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Facture{" +
            "id=" + getId() +
            ", dateFacturation='" + getDateFacturation() + "'" +
            ", annee=" + getAnnee() +
            ", mois=" + getMois() +
            ", nombreBonsLivraison='" + getNombreBonsLivraison() + "'" +
            ", totalQuantite=" + getTotalQuantite() +
            ", totalPrixHt=" + getTotalPrixHt() +
            ", totalPrixTtc='" + getTotalPrixTtc() + "'" +
            ", tva=" + getTva() +
            ", remise=" + getRemise() +
            "}";
    }

    public static Facture newInstance(float tva){
        Facture facture = new Facture();
        facture.setTva(tva);
        return facture;
    }

    public Facture withMoisAnnee(int mois, int annee){
        this.mois = mois;
        this.annee = annee;
        return this;
    }
    private static void hasValidMois(int mois) {
        if(mois < 1 || mois > 12){
            throw new BadRequestAlertException("The billing month or year is invalid", ENTITY_NAME, "facture.moisOrAnnee.invalid");
        }
    }

    public Facture withNombreBonsLivraison(long nombreBonsLivraison){
        this.nombreBonsLivraison = nombreBonsLivraison;
        return this;
    }

    private static void hasValidNombreBonsLivraison(long nombreBonsLivraison) {
        if(nombreBonsLivraison <= 0){
            throw new BadRequestAlertException("Number of deliveries must be positive", ENTITY_NAME, "facture.numberBl.invalid");
        }
    }

    public Facture withArticles(List<RecapitulatifFacturationClient> recapitulatifFacturationClientList){
        this.articles = new HashSet<>();
        for(RecapitulatifFacturationClient recapFacturationClient : recapitulatifFacturationClientList){
            Article article = Article.of(recapFacturationClient.getCodeProduit(), recapFacturationClient.getCategorieProduit(), recapFacturationClient.getTotalQuantiteeVendue(), recapFacturationClient.getTotalPrixVente());
            articles.add(article);
            totalQuantite += article.getQuantite();
            totalPrixHt += article.getMontant();
        }
        totalPrixTtc = totalPrixHt * (1 + tva);
        return this;
    }

    private static void hasValidQuantiteAndPrixHtAndPrixTtc(double quantite, double prixHt, double prixTtc) {
        if(quantite <= 0 || prixHt <= 0 || prixTtc <= 0){
            throw new BadRequestAlertException("The billing quantity or price is invalid", ENTITY_NAME, "facture.quantiteOrprix.invalid");
        }
    }

    private void hasValidRemise(Float remise) {
        if(remise < 0 || remise > 1){
            throw new BadRequestAlertException("The billing reduction is not valid", ENTITY_NAME, "facture.remise.invalid");
        }
    }

    public Facture validate(){
        hasValidMois(this.mois);
        hasValidNombreBonsLivraison(this.nombreBonsLivraison);
        hasValidQuantiteAndPrixHtAndPrixTtc(this.totalQuantite, this.totalPrixHt, this.totalPrixTtc);
        hasValidRemise(this.remise);
        return this;
    }

    public Facture withType(TypeFacture type) {
        this.type = type;
        return this;
    }

    public Facture withClient(long clientId) {
        Client client = new Client();
        client.setId(clientId);
        this.client = client;
        return this;
    }
    public Facture withRemise(Float remise) {
        this.remise = remise;
        return this;
    }
}
