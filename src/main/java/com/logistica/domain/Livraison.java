package com.logistica.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.logistica.domain.enumeration.TypeLivraison;
import com.logistica.domain.enumeration.Unite;
import com.logistica.service.dto.ICsvConvertible;
import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Optional;

/**
 * A Livraison.
 */
@Entity
@Table(name = "livraison")
@EntityListeners(AuditingEntityListener.class)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@JsonIgnoreProperties({"bonLivraison", "bonCommande", "bonFournisseur"})
public class Livraison implements ICsvConvertible, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "date_bon_commande")
    private LocalDate dateBonCommande;

    @Column(name = "numero_bon_commande")
    private Long numeroBonCommande;

    @Column(name = "bon_commande")
    private byte[] bonCommande;

    @Column(name = "bon_commande_mime_type")
    private String bonCommandeMimeType;

    @Column(name = "numero_bon_livraison", nullable = false)
    private Long numeroBonLivraison;

    @NotNull
    @Column(name = "date_bon_livraison", nullable = false)
    private LocalDate dateBonLivraison;

    @Column(name = "bon_livraison")
    private byte[] bonLivraison;

    @Column(name = "bon_livraison_mime_type")
    private String bonLivraisonMimeType;

    @Column(name = "numero_bon_fournisseur")
    private Long numeroBonFournisseur;

    @Column(name = "bon_fournisseur")
    private byte[] bonFournisseur;

    @Column(name = "bon_fournisseur_mime_type")
    private String bonFournisseurMimeType;

    @Column(name = "quantite_vendue")
    private Float quantiteVendue;

    @Enumerated(EnumType.STRING)
    @Column(name = "unite_vente")
    private Unite uniteVente;

    @Column(name = "prix_total_vente")
    private Float prixTotalVente;

    @Column(name = "quantite_achetee")
    private Float quantiteAchetee;

    @Enumerated(EnumType.STRING)
    @Column(name = "unite_achat")
    private Unite uniteAchat;

    @Column(name = "prix_total_achat")
    private Float prixTotalAchat;

    @Column(name = "quantite_convertie")
    private Float quantiteConvertie;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TypeLivraison type;

    @Column(name = "facture")
    private Boolean facture;

    @NotNull
    @Column(name = "date_bon_caisse", nullable = false)
    private LocalDate dateBonCaisse;

    @Column(name = "reparation_divers")
    private Float reparationDivers;

    @Column(name = "trax")
    private Float trax;

    @Column(name = "balance")
    private Float balance;

    @Column(name = "avance")
    private Float avance;

    @Column(name = "autoroute")
    private Float autoroute;

    @Column(name = "dernier_etat")
    private Float dernierEtat;

    @Column(name = "penalite_ese")
    private Float penaliteEse;

    @Column(name = "penalite_chfrs")
    private Float penaliteChfrs;

    @Column(name = "frais_espece")
    private Float fraisEspece;

    @Column(name = "retenu")
    private Float retenu;

    @Column(name = "total_comission")
    private Float totalComission;

    @ManyToOne
    @JsonIgnoreProperties("livraisons")
    private Fournisseur fournisseur;

    @ManyToOne
    @JsonIgnoreProperties("livraisons")
    private Client client;

    @ManyToOne
    @JsonIgnoreProperties("livraisons")
    private Transporteur transporteur;

    @ManyToOne
    @JsonIgnoreProperties("livraisons")
    private Trajet trajet;

    @ManyToOne
    @JsonIgnoreProperties("livraisons")
    private Produit produit;

    @ManyToOne
    @JsonIgnoreProperties("livraisons")
    private Societe societeFacturation;

    @Embedded
    private Audit audit = new Audit();

    public Audit getAudit() {
        return audit;
    }

    public void setAudit(Audit audit) {
        this.audit = audit;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateBonCommande() {
        return dateBonCommande;
    }

    public Livraison dateBonCommande(LocalDate dateBonCommande) {
        this.dateBonCommande = dateBonCommande;
        return this;
    }

    public void setDateBonCommande(LocalDate dateBonCommande) {
        this.dateBonCommande = dateBonCommande;
    }

    public Long getNumeroBonCommande() {
        return numeroBonCommande;
    }

    public Livraison numeroBonCommande(Long numeroBonCommande) {
        this.numeroBonCommande = numeroBonCommande;
        return this;
    }

    public void setNumeroBonCommande(Long numeroBonCommande) {
        this.numeroBonCommande = numeroBonCommande;
    }

    public Long getNumeroBonLivraison() {
        return numeroBonLivraison;
    }

    public Livraison numeroBonLivraison(Long numeroBonLivraison) {
        this.numeroBonLivraison = numeroBonLivraison;
        return this;
    }

    public void setNumeroBonLivraison(Long numeroBonLivraison) {
        this.numeroBonLivraison = numeroBonLivraison;
    }

    public LocalDate getDateBonLivraison() {
        return dateBonLivraison;
    }

    public Livraison dateBonLivraison(LocalDate dateBonLivraison) {
        this.dateBonLivraison = dateBonLivraison;
        return this;
    }

    public void setDateBonLivraison(LocalDate dateBonLivraison) {
        this.dateBonLivraison = dateBonLivraison;
    }

    public Long getNumeroBonFournisseur() {
        return numeroBonFournisseur;
    }

    public Livraison numeroBonFournisseur(Long numeroBonFournisseur) {
        this.numeroBonFournisseur = numeroBonFournisseur;
        return this;
    }

    public void setNumeroBonFournisseur(Long numeroBonFournisseur) {
        this.numeroBonFournisseur = numeroBonFournisseur;
    }

    public Float getQuantiteVendue() {
        return quantiteVendue;
    }

    public Livraison quantiteVendue(Float quantiteVendue) {
        this.quantiteVendue = quantiteVendue;
        return this;
    }

    public void setQuantiteVendue(Float quantiteVendue) {
        this.quantiteVendue = quantiteVendue;
    }

    public Unite getUniteVente() {
        return uniteVente;
    }

    public Livraison uniteVente(Unite uniteVente) {
        this.uniteVente = uniteVente;
        return this;
    }

    public void setUniteVente(Unite uniteVente) {
        this.uniteVente = uniteVente;
    }

    public Float getPrixTotalVente() {
        return prixTotalVente;
    }

    public Livraison prixTotalVente(Float prixTotalVente) {
        this.prixTotalVente = prixTotalVente;
        return this;
    }

    public void setPrixTotalVente(Float prixTotalVente) {
        this.prixTotalVente = prixTotalVente;
    }

    public Float getQuantiteAchetee() {
        return quantiteAchetee;
    }

    public Livraison quantiteAchetee(Float quantiteAchetee) {
        this.quantiteAchetee = quantiteAchetee;
        return this;
    }

    public void setQuantiteAchetee(Float quantiteAchetee) {
        this.quantiteAchetee = quantiteAchetee;
    }

    public Unite getUniteAchat() {
        return uniteAchat;
    }

    public Livraison uniteAchat(Unite uniteAchat) {
        this.uniteAchat = uniteAchat;
        return this;
    }

    public void setUniteAchat(Unite uniteAchat) {
        this.uniteAchat = uniteAchat;
    }

    public Float getPrixTotalAchat() {
        return prixTotalAchat;
    }

    public Livraison prixTotalAchat(Float prixTotalAchat) {
        this.prixTotalAchat = prixTotalAchat;
        return this;
    }

    public void setPrixTotalAchat(Float prixTotalAchat) {
        this.prixTotalAchat = prixTotalAchat;
    }

    public Float getQuantiteConvertie() {
        return quantiteConvertie;
    }

    public Livraison quantiteConvertie(Float quantiteConvertie) {
        this.quantiteConvertie = quantiteConvertie;
        return this;
    }

    public void setQuantiteConvertie(Float quantiteConvertie) {
        this.quantiteConvertie = quantiteConvertie;
    }

    public TypeLivraison getType() {
        return type;
    }

    public Livraison type(TypeLivraison type) {
        this.type = type;
        return this;
    }

    public void setType(TypeLivraison type) {
        this.type = type;
    }

    public Boolean isFacture() {
        return facture;
    }

    public Livraison facture(Boolean facture) {
        this.facture = facture;
        return this;
    }

    public void setFacture(Boolean facture) {
        this.facture = facture;
    }

    public LocalDate getDateBonCaisse() {
        return dateBonCaisse;
    }

    public Livraison dateBonCaisse(LocalDate dateBonCaisse) {
        this.dateBonCaisse = dateBonCaisse;
        return this;
    }

    public void setDateBonCaisse(LocalDate dateBonCaisse) {
        this.dateBonCaisse = dateBonCaisse;
    }

    public Float getReparationDivers() {
        return reparationDivers;
    }

    public Livraison reparationDivers(Float reparationDivers) {
        this.reparationDivers = reparationDivers;
        return this;
    }

    public void setReparationDivers(Float reparationDivers) {
        this.reparationDivers = reparationDivers;
    }

    public Float getTrax() {
        return trax;
    }

    public Livraison trax(Float trax) {
        this.trax = trax;
        return this;
    }

    public void setTrax(Float trax) {
        this.trax = trax;
    }

    public Float getBalance() {
        return balance;
    }

    public Livraison balance(Float balance) {
        this.balance = balance;
        return this;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }

    public Float getAvance() {
        return avance;
    }

    public Livraison avance(Float avance) {
        this.avance = avance;
        return this;
    }

    public void setAvance(Float avance) {
        this.avance = avance;
    }

    public Float getAutoroute() {
        return autoroute;
    }

    public Livraison autoroute(Float autoroute) {
        this.autoroute = autoroute;
        return this;
    }

    public void setAutoroute(Float autoroute) {
        this.autoroute = autoroute;
    }

    public Float getDernierEtat() {
        return dernierEtat;
    }

    public Livraison dernierEtat(Float dernierEtat) {
        this.dernierEtat = dernierEtat;
        return this;
    }

    public void setDernierEtat(Float dernierEtat) {
        this.dernierEtat = dernierEtat;
    }

    public Float getPenaliteEse() {
        return penaliteEse;
    }

    public Livraison penaliteEse(Float penaliteEse) {
        this.penaliteEse = penaliteEse;
        return this;
    }

    public void setPenaliteEse(Float penaliteEse) {
        this.penaliteEse = penaliteEse;
    }

    public Float getPenaliteChfrs() {
        return penaliteChfrs;
    }

    public Livraison penaliteChfrs(Float penaliteChfrs) {
        this.penaliteChfrs = penaliteChfrs;
        return this;
    }

    public void setPenaliteChfrs(Float penaliteChfrs) {
        this.penaliteChfrs = penaliteChfrs;
    }

    public Float getFraisEspece() {
        return fraisEspece;
    }

    public Livraison fraisEspece(Float fraisEspece) {
        this.fraisEspece = fraisEspece;
        return this;
    }

    public void setFraisEspece(Float fraisEspece) {
        this.fraisEspece = fraisEspece;
    }

    public Float getRetenu() {
        return retenu;
    }

    public Livraison retenu(Float retenu) {
        this.retenu = retenu;
        return this;
    }

    public void setRetenu(Float retenu) {
        this.retenu = retenu;
    }

    public Float getTotalComission() {
        return totalComission;
    }

    public Livraison totalComission(Float totalComission) {
        this.totalComission = totalComission;
        return this;
    }

    public void setTotalComission(Float totalComission) {
        this.totalComission = totalComission;
    }

    public Fournisseur getFournisseur() {
        return fournisseur;
    }

    public Livraison fournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
        return this;
    }

    public void setFournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
    }

    public Client getClient() {
        return client;
    }

    public Livraison client(Client client) {
        this.client = client;
        return this;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Transporteur getTransporteur() {
        return transporteur;
    }

    public Livraison transporteur(Transporteur transporteur) {
        this.transporteur = transporteur;
        return this;
    }

    public void setTransporteur(Transporteur transporteur) {
        this.transporteur = transporteur;
    }

    public Trajet getTrajet() {
        return trajet;
    }

    public Livraison trajet(Trajet trajet) {
        this.trajet = trajet;
        return this;
    }

    public void setTrajet(Trajet trajet) {
        this.trajet = trajet;
    }

    public Produit getProduit() {
        return produit;
    }

    public Livraison produit(Produit produit) {
        this.produit = produit;
        return this;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Livraison bonCommande(byte[] bonCommande) {
        this.bonCommande = bonCommande;
        return this;
    }

    public byte[] getBonCommande() {
        return bonCommande;
    }

    public void setBonCommande(byte[] bonCommande) {
        this.bonCommande = bonCommande;
    }

    public Livraison bonLivraison(byte[] bonLivraison) {
        this.bonLivraison = bonLivraison;
        return this;
    }

    public byte[] getBonLivraison() {
        return bonLivraison;
    }

    public void setBonLivraison(byte[] bonLivraison) {
        this.bonLivraison = bonLivraison;
    }

    public Livraison bonFournisseur(byte[] bonFournisseur) {
        this.bonFournisseur = bonFournisseur;
        return this;
    }

    public byte[] getBonFournisseur() {
        return bonFournisseur;
    }

    public void setBonFournisseur(byte[] bonFournisseur) {
        this.bonFournisseur = bonFournisseur;
    }

    public Livraison bonCommandeMimeType(String bonCommandeMimeType) {
        this.bonCommandeMimeType = bonCommandeMimeType;
        return this;
    }

    public String getBonCommandeMimeType() {
        return bonCommandeMimeType;
    }

    @JsonProperty("hasBonCommande")
    public Boolean hasBonCommande() {
        return ArrayUtils.isNotEmpty(this.bonCommande);
    }

    public void setBonCommandeMimeType(String bonCommandeMimeType) {
        this.bonCommandeMimeType = bonCommandeMimeType;
    }

    public Livraison bonLivraisonMimeType(String bonLivraisonMimeType) {
        this.bonLivraisonMimeType = bonLivraisonMimeType;
        return this;
    }

    public String getBonLivraisonMimeType() {
        return bonLivraisonMimeType;
    }

    public void setBonLivraisonMimeType(String bonLivraisonMimeType) {
        this.bonLivraisonMimeType = bonLivraisonMimeType;
    }

    @JsonProperty("hasBonLivraison")
    public Boolean hasBonLivraison() {
        return ArrayUtils.isNotEmpty(this.bonLivraison);
    }

    public Livraison bonFournisseurMimeType(String bonFournisseurMimeType) {
        this.bonFournisseurMimeType = bonFournisseurMimeType;
        return this;
    }

    public String getBonFournisseurMimeType() {
        return bonFournisseurMimeType;
    }

    public void setBonFournisseurMimeType(String bonFournisseurMimeType) {
        this.bonFournisseurMimeType = bonFournisseurMimeType;
    }

    @JsonProperty("hasBonFournisseur")
    public Boolean hasBonFournisseur() {
        return ArrayUtils.isNotEmpty(this.bonFournisseur);
    }

    public Societe getSocieteFacturation() {
        return societeFacturation;
    }

    public Livraison societeFacturation(Societe societe) {
        this.societeFacturation = societe;
        return this;
    }

    public void setSocieteFacturation(Societe societe) {
        this.societeFacturation = societe;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Livraison)) {
            return false;
        }
        return id != null && id.equals(((Livraison) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Livraison{" +
            "id=" + getId() +
            ", dateBonCommande='" + getDateBonCommande() + "'" +
            ", numeroBonCommande=" + getNumeroBonCommande() +
            ", numeroBonLivraison=" + getNumeroBonLivraison() +
            ", dateBonLivraison='" + getDateBonLivraison() + "'" +
            ", numeroBonFournisseur=" + getNumeroBonFournisseur() +
            ", quantiteVendue=" + getQuantiteVendue() +
            ", uniteVente='" + getUniteVente() + "'" +
            ", prixTotalVente=" + getPrixTotalVente() +
            ", quantiteAchetee=" + getQuantiteAchetee() +
            ", uniteAchat='" + getUniteAchat() + "'" +
            ", prixTotalAchat=" + getPrixTotalAchat() +
            ", quantiteConvertie=" + getQuantiteConvertie() +
            ", type='" + getType() + "'" +
            ", facture='" + isFacture() + "'" +
            ", dateBonCaisse='" + getDateBonCaisse() + "'" +
            ", reparationDivers=" + getReparationDivers() +
            ", trax=" + getTrax() +
            ", balance=" + getBalance() +
            ", avance=" + getAvance() +
            ", autoroute=" + getAutoroute() +
            ", dernierEtat=" + getDernierEtat() +
            ", penaliteEse=" + getPenaliteEse() +
            ", penaliteChfrs=" + getPenaliteChfrs() +
            ", fraisEspece=" + getFraisEspece() +
            ", retenu=" + getRetenu() +
            ", totalComission=" + getTotalComission() +
            "}";
    }

    public static String csvHeader() {
        return "numeroBonLivraison;client;depart;prixTotalAchat;fournisseur;destination;matricule;chauffeur";
    }

    @Override
    public String toCsv() {
        StringBuilder csv = new StringBuilder();
        csv.append(numeroBonLivraison).append(";")
            .append(Optional.ofNullable(client).map(Client::getNom).orElse("")).append(";")
            .append(Optional.ofNullable(trajet).map(Trajet::getDepart).orElse("")).append(";")
            .append(prixTotalAchat).append(";")
            .append(Optional.ofNullable(fournisseur).map(Fournisseur::getNom).orElse("")).append(";")
            .append(trajet.getDescription()).append(";")
            .append(Optional.ofNullable(transporteur).map(Transporteur::getMatricule).orElse("")).append(";")
            .append(transporteur.getNom() + " " + transporteur.getPrenom());
        return csv.toString();
    }
}
