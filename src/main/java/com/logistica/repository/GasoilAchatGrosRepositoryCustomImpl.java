package com.logistica.repository;

import com.logistica.service.dto.RecapitulatifGasoilAchatGros;
import com.logistica.service.dto.RecapitulatifGasoilGrosRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDate;

public class GasoilAchatGrosRepositoryCustomImpl implements GasoilAchatGrosRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<RecapitulatifGasoilAchatGros> getRecapitulatifGasoilAchatGros(RecapitulatifGasoilGrosRequest recapitulatifGasoilGrosRequest, Pageable pageable) {
        final Long fournisseurId = recapitulatifGasoilGrosRequest.getFournisseurId();
        final Long acheteurId = recapitulatifGasoilGrosRequest.getAcheteurId();
        final LocalDate dateDebut = recapitulatifGasoilGrosRequest.getDateDebut();
        final LocalDate dateFin = recapitulatifGasoilGrosRequest.getDateFin();

        StringBuilder query = new StringBuilder("Select new com.logistica.service.dto.RecapitulatifGasoilAchatGros(g.fournisseurGrossiste.nom, g.acheteur.nom, g.carburant.code, g.dateReception, g.quantity, g.uniteGasoilGros, g.prixUnitaire) From GasoilAchatGros g");
        boolean withFournisseurId = fournisseurId != null;
        boolean withAcheteurId = acheteurId != null;
        boolean withDateDebut = dateDebut != null;
        boolean withDateFin = dateFin != null;
        StringBuilder predicate = new StringBuilder(" Where 1=1 ");
        if (withFournisseurId) {
            predicate.append(" And g.fournisseurGrossiste.id = :fournisseurId");
        }
        if (withAcheteurId) {
            predicate.append(" And g.acheteur.id = :acheteurId");
        }
        if (withDateDebut) {
            predicate.append(" And g.dateReception >= :dateDebut");
        }
        if (withDateFin) {
            predicate.append(" And g.dateReception <= :dateFin");
        }
        String queryAsStr = query.append(predicate.toString()).toString();
        Query entityQuery = entityManager.createQuery(queryAsStr, RecapitulatifGasoilAchatGros.class);
        if (withFournisseurId) {
            entityQuery.setParameter("fournisseurId", fournisseurId);
        }
        if (withAcheteurId) {
            entityQuery.setParameter("acheteurId", acheteurId);
        }
        if (withDateDebut) {
            entityQuery.setParameter("dateDebut", dateDebut);
        }
        if (withDateFin) {
            entityQuery.setParameter("dateFin", dateFin);
        }
        if (pageable.isPaged()) {
            entityQuery.setFirstResult((int) pageable.getOffset());
            entityQuery.setMaxResults(pageable.getPageSize());
        }

        return PageableExecutionUtils.getPage(entityQuery.getResultList(), pageable, () -> {
            String countQuery = new StringBuilder("Select count(*) From GasoilAchatGros g ").append(predicate).toString();
            return entityManager.createQuery(countQuery).getFirstResult();
        });
    }
}
