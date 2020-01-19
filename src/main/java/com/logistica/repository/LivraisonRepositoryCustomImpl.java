package com.logistica.repository;

import com.logistica.domain.RecapitulatifAchat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class LivraisonRepositoryCustomImpl implements LivraisonRepositoryCustom {

	private final Logger log = LoggerFactory.getLogger(LivraisonRepositoryCustomImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	public Page<RecapitulatifAchat> getRecapitulatifByFournisseur(Long societeId, Long fournisseurId, Pageable pageable){
		StringBuilder query = new StringBuilder("Select new com.logistica.domain.RecapitulatifAchat(l.dateBonCommande, l.numeroBonCommande, l.produit.code, sum(l.quantiteAchetee), sum(l.quantiteConvertie), sum(l.prixTotalAchat)) From Livraison l");
		boolean withSocieteId = societeId != null;
		boolean withFournisseurId = fournisseurId != null;
		StringBuilder predicate = new StringBuilder();
		if(withSocieteId || withFournisseurId) {
			predicate.append(" Where ");
		}
		if(withSocieteId) {
			predicate.append(" l.transporteur.proprietaire.id = :societeId");
			if(withFournisseurId) {
				predicate.append(" And ");
			}
		}
		if(withFournisseurId) {
			predicate.append(" l.fournisseur.id = :fournisseurId");
		}
		String queryAsStr = query.append(predicate.toString()).append(" Group by l.dateBonCommande, l.numeroBonCommande, l.produit.code").toString();
		log.info(String.format("getRecapitulatifByFournisseur: %s", queryAsStr));
		Query entityQuery = entityManager.createQuery(queryAsStr, RecapitulatifAchat.class);
        if(withSocieteId) {
		    entityQuery.setParameter("societeId", societeId);
        }
        if(withFournisseurId){
		    entityQuery.setParameter("fournisseurId", fournisseurId);
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
}
