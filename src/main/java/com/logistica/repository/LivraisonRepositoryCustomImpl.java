package com.logistica.repository;

import com.logistica.domain.Livraison;
import com.logistica.domain.enumeration.TypeLivraison;
import com.logistica.service.dto.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class LivraisonRepositoryCustomImpl implements LivraisonRepositoryCustom {

	private final Logger log = LoggerFactory.getLogger(LivraisonRepositoryCustomImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	public Page<RecapitulatifAchat> getRecapitulatifAchat(RecapitulatifAchatRequest recapitulatifAchatRequest, Pageable pageable){
        final Long societeId = recapitulatifAchatRequest.getSocieteId();
        final Long fournisseurId = recapitulatifAchatRequest.getFournisseurId();
        final LocalDate dateDebutBonCommande = recapitulatifAchatRequest.getDateDebut();
        final LocalDate dateFinBonCommande = recapitulatifAchatRequest.getDateFin();

        StringBuilder query = new StringBuilder("Select new com.logistica.service.dto.RecapitulatifAchat(l.bonLivraisonMimeType, l.dateBonLivraison, l.numeroBonLivraison, l.bonCommandeMimeType, l.dateBonCommande, l.numeroBonCommande, l.fournisseur.nom, l.produit.code, l.transporteur.matricule, sum(l.quantiteAchetee), sum(l.quantiteConvertie), sum(l.prixTotalAchat)) From Livraison l");
        boolean withSocieteId = societeId != null;
        boolean withFournisseurId = fournisseurId != null;
        boolean withDateDebutCommande = dateDebutBonCommande != null;
        boolean withDateFinCommande = dateFinBonCommande != null;
        StringBuilder predicate = new StringBuilder(" Where 1=1 ");
        if (withSocieteId) {
            predicate.append(" And l.societeFacturation.id = :societeId");
        }
        if (withFournisseurId) {
            predicate.append(" And l.fournisseur.id = :fournisseurId");
        }
        if (withDateDebutCommande) {
            predicate.append(" And l.dateBonCommande >= :dateDebutBonCommande");
        }
        if (withDateFinCommande) {
            predicate.append(" And l.dateBonCommande <= :dateFinBonCommande");
        }
        String queryAsStr = query.append(predicate.toString()).append(" Group by l.bonLivraisonMimeType, l.dateBonLivraison, l.numeroBonLivraison, l.bonCommandeMimeType, l.dateBonCommande, l.numeroBonCommande, l.fournisseur.nom, l.produit.code, l.transporteur.matricule").toString();
        Query entityQuery = entityManager.createQuery(queryAsStr, RecapitulatifAchat.class);
        if (withSocieteId) {
            entityQuery.setParameter("societeId", societeId);
        }
        if (withFournisseurId) {
            entityQuery.setParameter("fournisseurId", fournisseurId);
        }
        if (withDateDebutCommande) {
            entityQuery.setParameter("dateDebutBonCommande", dateDebutBonCommande);
        }
        if(withDateFinCommande){
            entityQuery.setParameter("dateFinBonCommande", dateFinBonCommande);
        }
		if(pageable.isPaged()) {
			entityQuery.setFirstResult((int) pageable.getOffset());
			entityQuery.setMaxResults(pageable.getPageSize());
		}

		return PageableExecutionUtils.getPage(entityQuery.getResultList(), pageable, () -> {
            String countQuery = new StringBuilder("Select count(id) From Livraison l ").append(predicate).toString();
            return entityManager.createQuery(countQuery).getFirstResult();
        });
	}

    @Override
    public Page<Livraison> getSuiviTrajet(SuiviTrajetRequest suiviTrajetRequest, Pageable pageable) {
	    final Long societeId = suiviTrajetRequest.getSocieteId();
	    final Long clientId = suiviTrajetRequest.getClientId();
	    final Long fournisseurId = suiviTrajetRequest.getFournisseurId();
	    final Long trajetId = suiviTrajetRequest.getTrajetId();
	    final Long transporteurId = suiviTrajetRequest.getTransporteurId();
	    final LocalDate dateDebutLivraison = suiviTrajetRequest.getDateDebut();
	    final LocalDate dateFinLivraison = suiviTrajetRequest.getDateFin();

	    boolean withSociete = societeId != null;
	    boolean withClientId = clientId != null;
	    boolean withFournisseurId = fournisseurId != null;
	    boolean withTrajetId = trajetId != null;
	    boolean withTransporteurId = transporteurId != null;
	    boolean withDateDebutLivraison = dateDebutLivraison != null;
	    boolean withDateFinLivraison = dateFinLivraison != null;

        StringBuilder predicate = new StringBuilder(" Where 1=1");
        if(withSociete){
            predicate.append(" And l.societeFacturation.id = :societeId");
        }
        if(withClientId){
            predicate.append(" And l.client.id = :clientId");
        }
        if(withFournisseurId){
            predicate.append(" And l.fournisseur.id = :fournisseurId");
        }
        if(withTrajetId){
            predicate.append(" And l.trajet.id = :trajetId");
        }
        if(withTransporteurId){
            predicate.append(" And l.transporteur.id = :transporteurId");
        }
        if(withDateDebutLivraison){
            predicate.append(" And l.dateBonLivraison >= :dateDebutLivraison");
        }
        if(withDateFinLivraison){
            predicate.append(" And l.dateBonLivraison <= :dateFinLivraison");
        }

        StringBuilder query = new StringBuilder("From Livraison l ").append(predicate);
        Query entityQuery = entityManager.createQuery(query.toString());

        if(withSociete) {
            entityQuery.setParameter("societeId", societeId);
        }
        if(withClientId){
            entityQuery.setParameter("clientId", clientId);
        }
        if(withFournisseurId){
            entityQuery.setParameter("fournisseurId", fournisseurId);
        }
        if(withTrajetId){
            entityQuery.setParameter("trajetId", trajetId);
        }
        if(withTransporteurId){
            entityQuery.setParameter("transporteurId", transporteurId);
        }
        if(withDateDebutLivraison){
            entityQuery.setParameter("dateDebutLivraison", dateDebutLivraison);
        }
        if(withDateFinLivraison){
            entityQuery.setParameter("dateFinLivraison", dateFinLivraison);
        }

        if(pageable.isPaged()) {
            entityQuery.setFirstResult((int) pageable.getOffset());
            entityQuery.setMaxResults(pageable.getPageSize());
        }

        return PageableExecutionUtils.getPage(entityQuery.getResultList(), pageable, () -> {
            String countQuery = new StringBuilder("Select count(id) From Livraison l ").append(predicate).toString();
            return entityManager.createQuery(countQuery).getFirstResult();
        });
    }

    @Override
    public Page<RecapitulatifClient> getRecapitulatifClient(RecapitulatifClientRequest recapitulatifClientRequest, Pageable pageable) {
        final Long societeId = recapitulatifClientRequest.getSocieteId();
        final Long produitId = recapitulatifClientRequest.getProduitId();
        final Long clientId = recapitulatifClientRequest.getClientId();
        final Boolean isFacturee = recapitulatifClientRequest.isFacture();
        final TypeLivraison typeLivraison = recapitulatifClientRequest.getTypeLivraison();
        final String chantier = recapitulatifClientRequest.getChantier();
        final LocalDate dateDebutLivraison = recapitulatifClientRequest.getDateDebut();
        final LocalDate dateFinLivraison = recapitulatifClientRequest.getDateFin();
        boolean isMarchandise = typeLivraison == TypeLivraison.Marchandise;

        StringBuilder query = new StringBuilder("Select new com.logistica.service.dto.RecapitulatifClient(l.societeFacturation.nom, l.type, l.client.nom, l.bonLivraisonMimeType, l.dateBonLivraison, l.numeroBonLivraison, l.transporteur.matricule, l.produit.code, sum(l.quantiteVendue), l.uniteVente, sum(l.prixTotalVente), l.facture, l.chantier");
        if (isMarchandise) {
            query.append(", l.fournisseur.nom, l.quantiteAchetee, l.uniteAchat, l.prixTotalAchat");
        }
        query.append(") From Livraison l");
        boolean withSocieteId = societeId != null;
        boolean withProduitId = produitId != null;
        boolean withClientId = clientId != null;
        boolean withIsFacturee = isFacturee != null;
        boolean withTypeLivraison = typeLivraison != null;
        boolean withChantier = StringUtils.isNotBlank(chantier);
        boolean withDateDebutLivraison = dateDebutLivraison != null;
        boolean withDateFinLivraison = dateFinLivraison != null;

        StringBuilder predicate = new StringBuilder(" Where 1=1 ");
        if (withSocieteId) {
            predicate.append(" And l.societeFacturation.id = :societeId ");
        }
        if (withProduitId) {
            predicate.append(" And l.produit.id = :produitId ");
        }
        if (withClientId) {
            predicate.append(" And l.client.id = :clientId ");
        }
        if (withIsFacturee) {
            predicate.append(" And l.facture = :facture ");
        }
        if (withChantier) {
            predicate.append(" And l.chantier = :chantier");
        }
        if (withTypeLivraison) {
            predicate.append(" And l.type = :type");
        }
        if (withDateDebutLivraison) {
            predicate.append(" And l.dateBonLivraison >= :dateDebutLivraison");
        }
        if (withDateFinLivraison) {
            predicate.append(" And l.dateBonLivraison <= :dateFinLivraison");
        }
        query.append(predicate.toString()).append(" Group by l.societeFacturation.nom, l.type, l.client.nom, l.bonLivraisonMimeType, l.dateBonLivraison, l.numeroBonLivraison, l.transporteur.matricule, l.produit.code, l.uniteVente, l.facture, l.chantier");
        if (isMarchandise) {
            query.append(", l.fournisseur.nom, l.quantiteAchetee, l.uniteAchat, l.prixTotalAchat");
        }
        String queryAsStr = query.toString();
        Query entityQuery = entityManager.createQuery(queryAsStr, RecapitulatifClient.class);

        Optional.ofNullable(societeId).ifPresent(aSocieteId -> entityQuery.setParameter("societeId", aSocieteId));
        Optional.ofNullable(produitId).ifPresent(aProduitId -> entityQuery.setParameter("produitId", aProduitId));
        Optional.ofNullable(clientId).ifPresent(aClientId -> entityQuery.setParameter("clientId", aClientId));
        Optional.ofNullable(isFacturee).ifPresent(aFacture -> entityQuery.setParameter("facture", aFacture));
        Optional.ofNullable(typeLivraison).ifPresent(aTypeLivraison -> entityQuery.setParameter("type", aTypeLivraison));
        Optional.ofNullable(chantier).ifPresent(aChantier -> entityQuery.setParameter("chantier", chantier));
        Optional.ofNullable(dateDebutLivraison).ifPresent(aDateDebutLivraison -> entityQuery.setParameter("dateDebutLivraison", aDateDebutLivraison));
        Optional.ofNullable(dateFinLivraison).ifPresent(aDateFinLivraison -> entityQuery.setParameter("dateFinLivraison", aDateFinLivraison));

        if (pageable.isPaged()) {
            entityQuery.setFirstResult((int) pageable.getOffset());
            entityQuery.setMaxResults(pageable.getPageSize());
        }

        return PageableExecutionUtils.getPage(entityQuery.getResultList(), pageable, () -> {
            String countQuery = new StringBuilder("Select count(id) From Livraison l ").append(predicate).toString();
            return entityManager.createQuery(countQuery).getFirstResult();
        });
    }

    @Override
    public Page<RecapitulatifFacturation> getRecapitulatifFacturation(RecapitulatifFacturationRequest recapitulatifFacturationRequest, Pageable pageable) {
        final Long societeId = recapitulatifFacturationRequest.getSocieteId();
        final Boolean isFacturee = recapitulatifFacturationRequest.getFacture();
        final Long clientId = recapitulatifFacturationRequest.getClientId();
        final Long produitId = recapitulatifFacturationRequest.getProduitId();
        final String chantier = recapitulatifFacturationRequest.getChantier();
        final LocalDate dateDebutLivraison = recapitulatifFacturationRequest.getDateDebut();
        final LocalDate dateFinLivraison = recapitulatifFacturationRequest.getDateFin();

        boolean withSocieteId = societeId != null;
        boolean withIsFacturee = isFacturee != null;
        boolean withClientId = clientId != null;
        boolean withProduitId = produitId != null;
        boolean withChantier = chantier != null;
        boolean withDateDebutLivraison = dateDebutLivraison != null;
        boolean withDateFinLivraison = dateFinLivraison != null;

        StringBuilder query = new StringBuilder("Select new com.logistica.service.dto.RecapitulatifFacturation(month(l.dateBonLivraison),");
        if (withSocieteId) {
            query.append("l.societeFacturation.nom,");
        }
        query.append("l.produit.code, l.uniteVente,");
        if (withClientId) {
            query.append("l.client.nom,");
        }
        if (withIsFacturee) {
            query.append("l.facture,");
        }
        query.append(" sum(l.quantiteVendue), sum(l.prixTotalVente)) From Livraison l");

        StringBuilder predicate = new StringBuilder(" Where 1=1 ");
        if (withSocieteId) {
            predicate.append(" And l.societeFacturation.id = :societeId ");
        }
        if (withProduitId) {
            predicate.append(" And l.produit.id = :produitId ");
        }
        if (withIsFacturee) {
            predicate.append(" And l.facture = :facture ");
        }
        if (withClientId) {
            predicate.append(" And l.client.id = :clientId");
        }
        if (withDateDebutLivraison) {
            predicate.append(" And l.dateBonLivraison >= :dateDebutLivraison");
        }
        if (withDateFinLivraison) {
            predicate.append(" And l.dateBonLivraison <= :dateFinLivraison");
        }
        if (withChantier) {
            predicate.append(" And l.chantier = :chantier");
        }
        StringBuilder groupBy = new StringBuilder(" Group by MONTH(l.dateBonLivraison),");
        if (withSocieteId) {
            groupBy.append("l.societeFacturation.nom,");
        }
        groupBy.append("l.produit.code, l.uniteVente");
        if (withClientId) {
            groupBy.append(",l.client.nom");
        }
        if (withIsFacturee) {
            groupBy.append(",l.facture");
        }
        String queryAsStr = query.append(predicate.toString()).append(groupBy).toString();
        Query entityQuery = entityManager.createQuery(queryAsStr, RecapitulatifFacturation.class);

        Optional.ofNullable(societeId).ifPresent(aSocieteId -> entityQuery.setParameter("societeId", aSocieteId));
        Optional.ofNullable(produitId).ifPresent(aProduitId -> entityQuery.setParameter("produitId", aProduitId));
        Optional.ofNullable(isFacturee).ifPresent(aFacture -> entityQuery.setParameter("facture", aFacture));
        Optional.ofNullable(clientId).ifPresent(aClientId -> entityQuery.setParameter("clientId", aClientId));
        Optional.ofNullable(chantier).ifPresent(aChantier -> entityQuery.setParameter("chantier", chantier));
        Optional.ofNullable(dateDebutLivraison).ifPresent(aDateDebutLivraison -> entityQuery.setParameter("dateDebutLivraison", aDateDebutLivraison));
        Optional.ofNullable(dateFinLivraison).ifPresent(aDateFinLivraison -> entityQuery.setParameter("dateFinLivraison", aDateFinLivraison));

        if (pageable.isPaged()) {
            entityQuery.setFirstResult((int) pageable.getOffset());
            entityQuery.setMaxResults(pageable.getPageSize());
        }

        return PageableExecutionUtils.getPage(entityQuery.getResultList(), pageable, () -> {
            String countQuery = new StringBuilder("Select count(id) From Livraison l ").append(predicate).toString();
            return entityManager.createQuery(countQuery).getFirstResult();
        });
    }

    @Override
    public Page<RecapitulatifCaCamion> getRecapitulatifCaCamion(RecapitulatifCaCamionRequest recapitulatifCaCamionRequest, Pageable pageable) {
        final Long societeId = recapitulatifCaCamionRequest.getSocieteId();
        final Long clientId = recapitulatifCaCamionRequest.getClientId();
        final Long produitId = recapitulatifCaCamionRequest.getProduitId();

        boolean withSocieteId = societeId != null;
        boolean withClientId = clientId != null;
        boolean withProduitId = produitId != null;

        StringBuilder query = new StringBuilder("Select new com.logistica.service.dto.RecapitulatifCaCamion(l.transporteur.matricule, l.uniteVente, sum(l.quantiteVendue), sum(l.prixTotalVente)) From Livraison l");
        final StringBuilder predicate = new StringBuilder(" Where 1=1 ");
        if (withSocieteId) {
            predicate.append(" And l.societeFacturation.id = :societeId ");
        }
        if (withProduitId) {
            predicate.append(" And l.produit.id = :produitId ");
        }
        if (withClientId) {
            predicate.append(" And l.client.id = :clientId");
        }
        Optional.ofNullable(recapitulatifCaCamionRequest.getDateDebut()).ifPresent(aDateDebutLivraison -> predicate.append(" And l.dateBonLivraison >= :dateDebutLivraison"));
        Optional.ofNullable(recapitulatifCaCamionRequest.getDateFin()).ifPresent(aDateFinLivraison -> predicate.append(" And l.dateBonLivraison <= :dateFinLivraison"));
        String queryAsStr = query.append(predicate.toString()).append(" Group by l.transporteur.matricule, l.uniteVente").toString();
        Query entityQuery = entityManager.createQuery(queryAsStr, RecapitulatifCaCamion.class);

        Optional.ofNullable(societeId).ifPresent(aSocieteId -> entityQuery.setParameter("societeId", aSocieteId));
        Optional.ofNullable(produitId).ifPresent(aProduitId -> entityQuery.setParameter("produitId", aProduitId));
        Optional.ofNullable(clientId).ifPresent(aClientId -> entityQuery.setParameter("clientId", aClientId));
        Optional.ofNullable(recapitulatifCaCamionRequest.getDateDebut()).ifPresent(aDateDebutLivraison -> entityQuery.setParameter("dateDebutLivraison", aDateDebutLivraison));
        Optional.ofNullable(recapitulatifCaCamionRequest.getDateFin()).ifPresent(aDateFinLivraison -> entityQuery.setParameter("dateFinLivraison", aDateFinLivraison));
        if (pageable.isPaged()) {
            entityQuery.setFirstResult((int) pageable.getOffset());
            entityQuery.setMaxResults(pageable.getPageSize());
        }

        return PageableExecutionUtils.getPage(entityQuery.getResultList(), pageable, () -> {
            String countQuery = new StringBuilder("Select count(id) From Livraison l ").append(predicate).toString();
            return entityManager.createQuery(countQuery).getFirstResult();
        });
    }

    @Override
    public Page<RecapitulatifEfficaciteChauffeur> getRecapitulatifEfficaciteChauffeur(RecapitulatifEfficaciteChauffeurRequest recapitulatifEfficaciteChauffeurRequest, Pageable pageable) {
        final Long transporteurId = recapitulatifEfficaciteChauffeurRequest.getTransporteurId();
        final LocalDate dateDebutBonCaisse = recapitulatifEfficaciteChauffeurRequest.getDateDebut();
        final LocalDate dateFinBonCaisse = recapitulatifEfficaciteChauffeurRequest.getDateFin();

        boolean withTransporteurId = transporteurId != null;
        boolean withDateDebutBonCaisse = dateDebutBonCaisse != null;
        boolean withDateFinBonCaisse = dateFinBonCaisse != null;

        StringBuilder predicate = new StringBuilder(" Where 1=1 ");
        if (withTransporteurId) {
            predicate.append(" And l.transporteur.id = :transporteurId");
        }
        if (withDateDebutBonCaisse) {
            predicate.append(" And l.dateBonCaisse >= :dateDebut");
        }
        if (withDateFinBonCaisse) {
            predicate.append(" And l.dateBonCaisse <= :dateFin");
        }

        StringBuilder query = new StringBuilder("Select new com.logistica.service.dto.RecapitulatifEfficaciteChauffeur(l.transporteur.proprietaire.nom, l.transporteur.id, l.transporteur.prenom, l.transporteur.nom, count(l.trajet.id), sum(l.totalComission), sum(l.prixTotalVente)) From Livraison l ").append(predicate).append(" Group By l.transporteur.proprietaire.nom, l.transporteur.id, l.transporteur.prenom, l.transporteur.nom");
        Query entityQuery = entityManager.createQuery(query.toString());

        if (withTransporteurId) {
            entityQuery.setParameter("transporteurId", transporteurId);
        }
        if (withDateDebutBonCaisse) {
            entityQuery.setParameter("dateDebut", dateDebutBonCaisse);
        }
        if (withDateFinBonCaisse) {
            entityQuery.setParameter("dateFin", dateFinBonCaisse);
        }

        if (pageable.isPaged()) {
            entityQuery.setFirstResult((int) pageable.getOffset());
            entityQuery.setMaxResults(pageable.getPageSize());
        }

        return PageableExecutionUtils.getPage(entityQuery.getResultList(), pageable, () -> {
            String countQuery = new StringBuilder("Select count(id) From Livraison l ").append(predicate).toString();
            return entityManager.createQuery(countQuery).getFirstResult();
        });
    }

    @Override
    public List<ChiffreAffaireParMois> getEvolutionChiffreAffaireParMois(StatistiquesChiffreAffaireRequest evolutionCARequest) {
        final Long societeId = evolutionCARequest.getSocieteId();
        final Long produitId = evolutionCARequest.getProduitId();
        final Long trajetId = evolutionCARequest.getTrajetId();
        final TypeLivraison typeLivraison = evolutionCARequest.getTypeLivraison();
        final List<String> matriculesToInclude = evolutionCARequest.getMatriculesToInclude();
        final List<String> matriculesToExclude = evolutionCARequest.getMatriculesToExclude();
        final LocalDate dateDebutBonLivraison = evolutionCARequest.getDateDebut();
        final LocalDate dateFinBonLivraison = evolutionCARequest.getDateFin();

        boolean withSocieteId = societeId != null;
        boolean withProduitId = produitId != null;
        boolean withTrajetId = trajetId != null;
        boolean withTypeLivraison = typeLivraison != null;
        boolean withMatriculesToInclude = !CollectionUtils.isEmpty(matriculesToInclude);
        boolean withMatriculesToExclude = !CollectionUtils.isEmpty(matriculesToExclude);
        boolean withDateDebutBonLivraison = dateDebutBonLivraison != null;
        boolean withDateFinBonLivraison = dateFinBonLivraison != null;

        StringBuilder predicate = new StringBuilder(" Where 1=1 ");
        if (withSocieteId) {
            predicate.append(" And l.societeFacturation.id = :societeId");
        }
        if (withProduitId) {
            predicate.append(" And l.produit.id = :produitId");
        }
        if (withTrajetId) {
            predicate.append(" And l.trajet.id = :trajetId");
        }
        if (withTypeLivraison) {
            predicate.append(" And l.type = :type");
        }
        if (withMatriculesToInclude) {
            predicate.append(" And l.transporteur.matricule in (:matriculesToInclude)");
        }
        if (withMatriculesToExclude) {
            predicate.append(" And l.transporteur.matricule not in (:matriculesToExclude)");
        }
        if (withDateDebutBonLivraison) {
            predicate.append(" And l.dateBonLivraison >= :dateDebut");
        }
        if (withDateFinBonLivraison) {
            predicate.append(" And l.dateBonLivraison <= :dateFin");
        }

        StringBuilder query = new StringBuilder("Select new com.logistica.service.dto.ChiffreAffaireParMois(year(dateBonLivraison), month(dateBonLivraison), sum(prixTotalVente)) From Livraison l").append(predicate).append(" Group By year(dateBonLivraison), month(dateBonLivraison)");
        Query entityQuery = entityManager.createQuery(query.toString());

        if (withSocieteId) {
            entityQuery.setParameter("societeId", societeId);
        }
        if (withProduitId) {
            entityQuery.setParameter("produitId", produitId);
        }
        if (withTrajetId) {
            entityQuery.setParameter("trajetId", trajetId);
        }
        if (withTypeLivraison) {
            entityQuery.setParameter("type", typeLivraison);
        }
        if (withMatriculesToInclude) {
            entityQuery.setParameter("matriculesToInclude", matriculesToInclude);
        }
        if (withMatriculesToExclude) {
            entityQuery.setParameter("matriculesToExclude", matriculesToExclude);
        }
        if (withDateDebutBonLivraison) {
            entityQuery.setParameter("dateDebut", dateDebutBonLivraison);
        }
        if (withDateFinBonLivraison) {
            entityQuery.setParameter("dateFin", dateFinBonLivraison);
        }
        return entityQuery.getResultList();
    }

    @Override
    public List<ChiffreAffaireParRepartition> getRepartitionChiffreAffairePar(StatistiquesChiffreAffaireRequest evolutionCARequest, UniteRepartition uniteRepartition) {
        final Long societeId = evolutionCARequest.getSocieteId();
        final Long produitId = evolutionCARequest.getProduitId();
        final Long trajetId = evolutionCARequest.getTrajetId();
        final TypeLivraison typeLivraison = evolutionCARequest.getTypeLivraison();
        final List<String> matriculesToInclude = evolutionCARequest.getMatriculesToInclude();
        final List<String> matriculesToExclude = evolutionCARequest.getMatriculesToExclude();
        final LocalDate dateDebutBonLivraison = evolutionCARequest.getDateDebut();
        final LocalDate dateFinBonLivraison = evolutionCARequest.getDateFin();

        boolean withSocieteId = societeId != null;
        boolean withProduitId = produitId != null;
        boolean withTrajetId = trajetId != null;
        boolean withTypeLivraison = typeLivraison != null;
        boolean withMatriculesToInclude = !CollectionUtils.isEmpty(matriculesToInclude);
        boolean withMatriculesToExclude = !CollectionUtils.isEmpty(matriculesToExclude);
        boolean withDateDebutBonLivraison = dateDebutBonLivraison != null;
        boolean withDateFinBonLivraison = dateFinBonLivraison != null;

        StringBuilder predicate = new StringBuilder(" Where 1=1 ");
        if (withSocieteId) {
            predicate.append(" And l.societeFacturation.id = :societeId");
        }
        if (withProduitId) {
            predicate.append(" And l.produit.id = :produitId");
        }
        if (withTrajetId) {
            predicate.append(" And l.trajet.id = :trajetId");
        }
        if (withTypeLivraison) {
            predicate.append(" And l.type = :type");
        }
        if (withMatriculesToInclude) {
            predicate.append(" And l.transporteur.matricule in (:matriculesToInclude)");
        }
        if (withDateDebutBonLivraison) {
            predicate.append(" And l.dateBonLivraison >= :dateDebut");
        }
        if (withDateFinBonLivraison) {
            predicate.append(" And l.dateBonLivraison <= :dateFin");
        }
        if (withMatriculesToExclude) {
            predicate.append(" And l.transporteur.matricule not in (:matriculesToExclude)");
        }

        StringBuilder query = new StringBuilder("Select new com.logistica.service.dto.ChiffreAffaireParRepartition(str(" + uniteRepartition.getter() + "), sum(prixTotalVente)) From Livraison l").append(predicate).append(" Group By ").append(uniteRepartition.getter()).append(" Order by sum(prixTotalVente) desc");
        Query entityQuery = entityManager.createQuery(query.toString());

        if (withSocieteId) {
            entityQuery.setParameter("societeId", societeId);
        }
        if (withProduitId) {
            entityQuery.setParameter("produitId", produitId);
        }
        if (withTrajetId) {
            entityQuery.setParameter("trajetId", trajetId);
        }
        if (withTypeLivraison) {
            entityQuery.setParameter("type", typeLivraison);
        }
        if (withMatriculesToInclude) {
            entityQuery.setParameter("matriculesToInclude", matriculesToInclude);
        }
        if (withDateDebutBonLivraison) {
            entityQuery.setParameter("dateDebut", dateDebutBonLivraison);
        }
        if (withDateFinBonLivraison) {
            entityQuery.setParameter("dateFin", dateFinBonLivraison);
        }
        if (withMatriculesToExclude) {
            entityQuery.setParameter("matriculesToExclude", matriculesToExclude);
        }
        return entityQuery.getResultList();
    }

    @Override
    public Double getTotalChiffreAffaire(StatistiquesChiffreAffaireRequest evolutionCARequest) {
        final Long societeId = evolutionCARequest.getSocieteId();
        final Long produitId = evolutionCARequest.getProduitId();
        final Long trajetId = evolutionCARequest.getTrajetId();
        final TypeLivraison typeLivraison = evolutionCARequest.getTypeLivraison();
        final List<String> matriculesToInclude = evolutionCARequest.getMatriculesToInclude();
        final List<String> matriculesToExclude = evolutionCARequest.getMatriculesToExclude();
        final LocalDate dateDebutBonLivraison = evolutionCARequest.getDateDebut();
        final LocalDate dateFinBonLivraison = evolutionCARequest.getDateFin();

        boolean withSocieteId = societeId != null;
        boolean withProduitId = produitId != null;
        boolean withTrajetId = trajetId != null;
        boolean withTypeLivraison = typeLivraison != null;
        boolean withMatriculesToInclude = !CollectionUtils.isEmpty(matriculesToInclude);
        boolean withMatriculesToExclude = !CollectionUtils.isEmpty(matriculesToExclude);
        boolean withDateDebutBonLivraison = dateDebutBonLivraison != null;
        boolean withDateFinBonLivraison = dateFinBonLivraison != null;

        StringBuilder predicate = new StringBuilder(" Where 1=1 ");
        if (withSocieteId) {
            predicate.append(" And l.societeFacturation.id = :societeId");
        }
        if (withProduitId) {
            predicate.append(" And l.produit.id = :produitId");
        }
        if (withTrajetId) {
            predicate.append(" And l.trajet.id = :trajetId");
        }
        if (withTypeLivraison) {
            predicate.append(" And l.type = :type");
        }
        if (withMatriculesToInclude) {
            predicate.append(" And l.transporteur.matricule in (:matriculesToInclude)");
        }
        if (withMatriculesToExclude) {
            predicate.append(" And l.transporteur.matricule not in (:matriculesToExclude)");
        }
        if (withDateDebutBonLivraison) {
            predicate.append(" And l.dateBonLivraison >= :dateDebut");
        }
        if (withDateFinBonLivraison) {
            predicate.append(" And l.dateBonLivraison <= :dateFin");
        }

        StringBuilder query = new StringBuilder("Select sum(prixTotalVente) as chiffreAffaire From Livraison l").append(predicate);
        Query entityQuery = entityManager.createQuery(query.toString());

        if (withSocieteId) {
            entityQuery.setParameter("societeId", societeId);
        }
        if (withProduitId) {
            entityQuery.setParameter("produitId", produitId);
        }
        if (withTrajetId) {
            entityQuery.setParameter("trajetId", trajetId);
        }
        if (withTypeLivraison) {
            entityQuery.setParameter("type", typeLivraison);
        }
        if (withMatriculesToInclude) {
            entityQuery.setParameter("matriculesToInclude", matriculesToInclude);
        }
        if (withMatriculesToExclude) {
            entityQuery.setParameter("matriculesToExclude", matriculesToExclude);
        }
        if (withDateDebutBonLivraison) {
            entityQuery.setParameter("dateDebut", dateDebutBonLivraison);
        }
        if (withDateFinBonLivraison) {
            entityQuery.setParameter("dateFin", dateFinBonLivraison);
        }
        return (Double) entityQuery.getSingleResult();
    }

    public List<StockDepot> getTotalAchatMarchandisesByDepotAndUnite(RecapitulatifDepotAggregatStockRequest depotAggregatStockRequest) {
        return getStockDepots(depotAggregatStockRequest, "Select new com.logistica.service.dto.StockDepot(l.depotAggregat.nom, l.uniteAchat, sum(l.quantiteAchetee)) From Livraison %s l Group By l.depotAggregat.nom, l.uniteAchat");
    }

    public List<StockDepot> getTotalVenteMarchandisesByDepotAndUnite(RecapitulatifDepotAggregatStockRequest depotAggregatStockRequest) {
        return getStockDepots(depotAggregatStockRequest, "Select new com.logistica.service.dto.StockDepot(l.depotAggregat.nom, l.uniteVente, sum(l.quantiteVendue)) From Livraison %s l Group By l.depotAggregat.nom, l.uniteVente");

    }

    private List<StockDepot> getStockDepots(RecapitulatifDepotAggregatStockRequest depotAggregatStockRequest, String queryTemplate) {
        Optional<LocalDate> dateDebutOptional = Optional.ofNullable(depotAggregatStockRequest.getDateDebut());
        String dateDebutCriteria = dateDebutOptional.map(dateDebut -> "Where l.dateBonLivraison >= :dateDebut").orElse("");
        TypedQuery<StockDepot> entityQuery = entityManager.createQuery(String.format(queryTemplate, dateDebutCriteria), StockDepot.class);
        dateDebutOptional.ifPresent(dateDebut -> entityQuery.setParameter("dateDebut", dateDebut));

        return entityQuery.getResultList();
    }
}
