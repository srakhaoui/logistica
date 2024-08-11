package com.logistica.repository;

import com.logistica.domain.Facture;
import com.logistica.domain.enumeration.TypeLivraison;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.Optional;

public class FactureRepositoryCustomImpl implements FactureRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    public Page<Facture> getFactures(Long societeId, Boolean facture, Long clientId, LocalDate dateDebutLivraison, LocalDate  dateFinLivraison, String chantier, TypeLivraison typeLivraison, Boolean regleEnEspece, Long produitId, Pageable pageable) {
        StringBuilder selecQueryBuilder = new StringBuilder("Select distinct f");
        StringBuilder fromQueryBuilder = new StringBuilder(" From Livraison l inner join Facture f on l.factureId = f.id ")
            .append("Where l.dateBonLivraison >= :dateDebutLivraison And l.dateBonLivraison <= :dateFinLivraison And l.type = :typeLivraison ");
        Optional.ofNullable(societeId).ifPresent(aSocieteId -> fromQueryBuilder.append(" And l.societeFacturation.id = :societeId"));
        Optional.ofNullable(clientId).ifPresent(aClientId -> fromQueryBuilder.append(" And l.client.id = :clientId"));
        Optional.ofNullable(chantier).ifPresent(aChantier -> fromQueryBuilder.append(" And l.chantier = :chantier"));
        Optional.ofNullable(produitId).ifPresent(aProductId -> fromQueryBuilder.append(" And l.produit.id = :produitId"));
        Optional.ofNullable(facture).ifPresent(aFacture -> fromQueryBuilder.append(" And l.facture = :facture"));
        Optional.ofNullable(regleEnEspece).ifPresent(aRegleEnEspece -> fromQueryBuilder.append(String.format(" And l.reglementEspeceId is %s null", regleEnEspece ? "not" : "")));

        TypedQuery<Facture> entityQuery = entityManager.createQuery(selecQueryBuilder.append(fromQueryBuilder).toString(), Facture.class);
        Optional.ofNullable(societeId).ifPresent(aSocieteId -> entityQuery.setParameter("societeId", aSocieteId));
        Optional.ofNullable(clientId).ifPresent(aClientId -> entityQuery.setParameter("clientId", aClientId));
        entityQuery.setParameter("dateDebutLivraison", dateDebutLivraison);
        entityQuery.setParameter("dateFinLivraison", dateFinLivraison);
        entityQuery.setParameter("typeLivraison", typeLivraison);
        Optional.ofNullable(facture).ifPresent(aFacture -> entityQuery.setParameter("facture", aFacture));
        Optional.ofNullable(chantier).ifPresent(aChantier -> entityQuery.setParameter("chantier", chantier));
        Optional.ofNullable(produitId).ifPresent(aProduitId -> entityQuery.setParameter("produitId", aProduitId));

        if(pageable.isPaged()) {
            entityQuery.setFirstResult((int) pageable.getOffset());
            entityQuery.setMaxResults(pageable.getPageSize());
        }

        return PageableExecutionUtils.getPage(entityQuery.getResultList(), pageable, () -> {
            String countQuery = new StringBuilder("Select count(id) ").append(fromQueryBuilder).toString();
            return entityManager.createQuery(countQuery).getFirstResult();
        });
    }
}
