package com.logistica.repository;

import com.logistica.domain.Livraison;
import com.logistica.domain.enumeration.TypeLivraison;
import com.logistica.service.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDate;
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

		StringBuilder query = new StringBuilder("Select new com.logistica.service.dto.RecapitulatifAchat(l.dateBonCommande, l.numeroBonCommande, l.produit.code, sum(l.quantiteAchetee), sum(l.quantiteConvertie), sum(l.prixTotalAchat)) From Livraison l");
		boolean withSocieteId = societeId != null;
		boolean withFournisseurId = fournisseurId != null;
		boolean withDateDebutCommande = dateDebutBonCommande != null;
		boolean withDateFinCommande = dateFinBonCommande != null;
		StringBuilder predicate = new StringBuilder(" Where 1=1 ");
		if(withSocieteId) {
			predicate.append(" And l.societeFacturation.id = :societeId");
		}
		if(withFournisseurId) {
			predicate.append(" And l.fournisseur.id = :fournisseurId");
		}
		if(withDateDebutCommande){
		    predicate.append(" And l.dateBonCommande >= :dateDebutBonCommande");
        }
		if(withDateFinCommande){
            predicate.append(" And l.dateBonCommande <= :dateFinBonCommande");
        }
		String queryAsStr = query.append(predicate.toString()).append(" Group by l.dateBonCommande, l.numeroBonCommande, l.produit.code").toString();
		Query entityQuery = entityManager.createQuery(queryAsStr, RecapitulatifAchat.class);
        if(withSocieteId) {
		    entityQuery.setParameter("societeId", societeId);
        }
        if(withFournisseurId){
		    entityQuery.setParameter("fournisseurId", fournisseurId);
        }
        if(withDateDebutCommande){
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
			String countQuery = new StringBuilder("Select count(id) From Livraison ").append(predicate).toString();
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
            String countQuery = new StringBuilder("Select count(id) From Livraison ").append(predicate).toString();
            return entityManager.createQuery(countQuery).getFirstResult();
        });
    }

    @Override
    public Page<RecapitulatifClient> getRecapitulatifClient(RecapitulatifClientRequest recapitulatifClientRequest, Pageable pageable) {
	    final Long societeId = recapitulatifClientRequest.getSocieteId();
	    final Boolean isFacturee = recapitulatifClientRequest.isFacture();
	    final TypeLivraison typeLivraison = recapitulatifClientRequest.getTypeLivraison();
	    final LocalDate dateDebutLivraison = recapitulatifClientRequest.getDateDebut();
	    final LocalDate dateFinLivraison = recapitulatifClientRequest.getDateFin();

        StringBuilder query = new StringBuilder("Select new com.logistica.service.dto.RecapitulatifClient(l.client.nom, l.dateBonLivraison, l.numeroBonLivraison, l.transporteur.matricule, l.produit.code, sum(l.quantiteVendue), sum(l.prixTotalVente)) From Livraison l");
        boolean withSocieteId = societeId != null;
        boolean withIsFacturee = isFacturee != null;
        boolean withTypeLivraison = typeLivraison != null;
        boolean withDateDebutLivraison = dateDebutLivraison != null;
        boolean withDateFinLivraison = dateFinLivraison != null;

        StringBuilder predicate = new StringBuilder(" Where 1=1 ");
        if(withSocieteId){
            predicate.append(" And l.societeFacturation.id = :societeId ");
        }
        if(withIsFacturee){
            predicate.append(" And l.facture = :facture ");
        }
        if(withTypeLivraison){
            predicate.append(" And l.type = :type");
        }
        if(withDateDebutLivraison){
            predicate.append(" And l.dateBonLivraison >= :dateDebutLivraison");
        }
        if(withDateFinLivraison){
            predicate.append(" And l.dateBonLivraison <= :dateFinLivraison");
        }
        String queryAsStr = query.append(predicate.toString()).append(" Group by l.client.nom, l.dateBonLivraison, l.numeroBonLivraison, l.transporteur.matricule, l.produit.code").toString();
        Query entityQuery = entityManager.createQuery(queryAsStr, RecapitulatifClient.class);

        Optional.ofNullable(societeId).ifPresent(aSocieteId -> entityQuery.setParameter("societeId", aSocieteId));
        Optional.ofNullable(isFacturee).ifPresent(aFacture -> entityQuery.setParameter("facture", aFacture));
        Optional.ofNullable(typeLivraison).ifPresent(aTypeLivraison -> entityQuery.setParameter("type", aTypeLivraison));
        Optional.ofNullable(dateDebutLivraison).ifPresent(aDateDebutLivraison -> entityQuery.setParameter("dateDebutLivraison", aDateDebutLivraison));
        Optional.ofNullable(dateFinLivraison).ifPresent(aDateFinLivraison -> entityQuery.setParameter("dateFinLivraison", aDateFinLivraison));

        if(pageable.isPaged()) {
            entityQuery.setFirstResult((int) pageable.getOffset());
            entityQuery.setMaxResults(pageable.getPageSize());
        }

        return PageableExecutionUtils.getPage(entityQuery.getResultList(), pageable, () -> {
            String countQuery = new StringBuilder("Select count(id) From Livraison ").append(predicate).toString();
            return entityManager.createQuery(countQuery).getFirstResult();
        });
    }

    @Override
    public Page<RecapitulatifFacturation> getRecapitulatifFacturation(RecapitulatifFacturationRequest recapitulatifFacturationRequest, Pageable pageable) {
	    final Long societeId = recapitulatifFacturationRequest.getSocieteId();
	    final Boolean isFacturee = recapitulatifFacturationRequest.getFacture();
	    final Long clientId = recapitulatifFacturationRequest.getClientId();
        final LocalDate dateDebutLivraison = recapitulatifFacturationRequest.getDateDebut();
        final LocalDate dateFinLivraison = recapitulatifFacturationRequest.getDateFin();

        StringBuilder query = new StringBuilder("Select new com.logistica.service.dto.RecapitulatifFacturation(month(l.dateBonLivraison), l.produit.code, l.uniteVente, sum(l.quantiteVendue), sum(l.prixTotalVente)) From Livraison l");
        boolean withSocieteId = societeId != null;
        boolean withIsFacturee = isFacturee != null;
        boolean withClientId = clientId != null;
        boolean withDateDebutLivraison = dateDebutLivraison != null;
        boolean withDateFinLivraison = dateFinLivraison != null;

        StringBuilder predicate = new StringBuilder(" Where 1=1 ");
        if(withSocieteId){
            predicate.append(" And l.societeFacturation.id = :societeId ");
        }
        if(withIsFacturee){
            predicate.append(" And l.facture = :facture ");
        }
        if(withClientId){
            predicate.append(" And l.client.id = :clientId");
        }
        if(withDateDebutLivraison){
            predicate.append(" And l.dateBonLivraison >= :dateDebutLivraison");
        }
        if(withDateFinLivraison){
            predicate.append(" And l.dateBonLivraison <= :dateFinLivraison");
        }
        String queryAsStr = query.append(predicate.toString()).append(" Group by MONTH(l.dateBonLivraison), l.produit.code, l.uniteVente").toString();
        Query entityQuery = entityManager.createQuery(queryAsStr, RecapitulatifFacturation.class);

        Optional.ofNullable(societeId).ifPresent(aSocieteId -> entityQuery.setParameter("societeId", aSocieteId));
        Optional.ofNullable(isFacturee).ifPresent(aFacture -> entityQuery.setParameter("facture", aFacture));
        Optional.ofNullable(clientId).ifPresent(aClientId -> entityQuery.setParameter("clientId", aClientId));
        Optional.ofNullable(dateDebutLivraison).ifPresent(aDateDebutLivraison -> entityQuery.setParameter("dateDebutLivraison", aDateDebutLivraison));
        Optional.ofNullable(dateFinLivraison).ifPresent(aDateFinLivraison -> entityQuery.setParameter("dateFinLivraison", aDateFinLivraison));

        if(pageable.isPaged()) {
            entityQuery.setFirstResult((int) pageable.getOffset());
            entityQuery.setMaxResults(pageable.getPageSize());
        }

        return PageableExecutionUtils.getPage(entityQuery.getResultList(), pageable, () -> {
            String countQuery = new StringBuilder("Select count(id) From Livraison ").append(predicate).toString();
            return entityManager.createQuery(countQuery).getFirstResult();
        });
    }

    @Override
    public Page<RecapitulatifCaCamion> getRecapitulatifCaCamion(RecapitulatifCaCamionRequest recapitulatifCaCamionRequest, Pageable pageable) {
        StringBuilder query = new StringBuilder("Select new com.logistica.service.dto.RecapitulatifCaCamion(l.transporteur.matricule, l.uniteVente, sum(l.quantiteVendue), sum(l.prixTotalVente)) From Livraison l");
        final StringBuilder predicate = new StringBuilder(" Where 1=1 ");
        Optional.ofNullable(recapitulatifCaCamionRequest.getDateDebut()).ifPresent(aDateDebutLivraison -> predicate.append(" And l.dateBonLivraison >= :dateDebutLivraison"));
        Optional.ofNullable(recapitulatifCaCamionRequest.getDateFin()).ifPresent(aDateFinLivraison -> predicate.append(" And l.dateBonLivraison <= :dateFinLivraison"));
        String queryAsStr = query.append(predicate.toString()).append(" Group by l.transporteur.matricule, l.uniteVente").toString();
        Query entityQuery = entityManager.createQuery(queryAsStr, RecapitulatifCaCamion.class);

        Optional.ofNullable(recapitulatifCaCamionRequest.getDateDebut()).ifPresent(aDateDebutLivraison -> entityQuery.setParameter("dateDebutLivraison", aDateDebutLivraison));
        Optional.ofNullable(recapitulatifCaCamionRequest.getDateFin()).ifPresent(aDateFinLivraison -> entityQuery.setParameter("dateFinLivraison", aDateFinLivraison));
        if(pageable.isPaged()) {
            entityQuery.setFirstResult((int) pageable.getOffset());
            entityQuery.setMaxResults(pageable.getPageSize());
        }

        return PageableExecutionUtils.getPage(entityQuery.getResultList(), pageable, () -> {
            String countQuery = new StringBuilder("Select count(id) From Livraison ").append(predicate).toString();
            return entityManager.createQuery(countQuery).getFirstResult();
        });
    }
}
