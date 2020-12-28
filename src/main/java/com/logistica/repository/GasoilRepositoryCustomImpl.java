package com.logistica.repository;

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
import java.util.List;

public class GasoilRepositoryCustomImpl implements GasoilRepositoryCustom {

    private final Logger log = LoggerFactory.getLogger(GasoilRepositoryCustomImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<RecapitulatifChargeGasoil> getRecapitulatifChargeGasoil(RecapitulatifChargeGasoilRequest recapitulatifChargeGasoilRequest, Pageable pageable) {
        final Long societeId = recapitulatifChargeGasoilRequest.getSocieteId();
        final Long transporteurId = recapitulatifChargeGasoilRequest.getTransporteurId();
        final LocalDate dateDebut = recapitulatifChargeGasoilRequest.getDateDebut();
        final LocalDate dateFin = recapitulatifChargeGasoilRequest.getDateFin();

        StringBuilder query = new StringBuilder("Select new com.logistica.service.dto.RecapitulatifChargeGasoil(g.societeFacturation.id, g.societeFacturation.nom, g.transporteur.id, g.transporteur.nom, g.transporteur.prenom, g.transporteur.matricule, sum(g.quantiteEnLitre), avg(g.prixDuLitre), sum(g.prixTotalGasoil), sum(g.kilometrageParcouru)) From Gasoil g");
        boolean withSocieteId = societeId != null;
        boolean withTransporteurId = transporteurId != null;
        boolean withDateDebut = dateDebut != null;
        boolean withDateFin = dateFin != null;
        StringBuilder predicate = new StringBuilder(" Where 1=1 ");
        if (withSocieteId) {
            predicate.append(" And g.societeFacturation.id = :societeId");
        }
        if (withTransporteurId) {
            predicate.append(" And g.transporteur.id = :transporteurId");
        }
        if (withDateDebut) {
            predicate.append(" And g.dateBonGasoil >= :dateDebut");
        }
        if (withDateFin) {
            predicate.append(" And g.dateBonGasoil <= :dateFin");
        }
        String queryAsStr = query.append(predicate.toString()).append(" Group by g.societeFacturation.id, g.societeFacturation.nom, g.transporteur.id, g.transporteur.nom, g.transporteur.prenom, g.transporteur.matricule").toString();
        Query entityQuery = entityManager.createQuery(queryAsStr, RecapitulatifChargeGasoil.class);
        if (withSocieteId) {
            entityQuery.setParameter("societeId", societeId);
        }
        if (withTransporteurId) {
            entityQuery.setParameter("transporteurId", transporteurId);
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
            String countQuery = new StringBuilder("Select count(*) From Gasoil g ").append(predicate).toString();
            return entityManager.createQuery(countQuery).getFirstResult();
        });
    }

    @Override
    public List<ChargeGasoilParMois> getEvolutionChargeGasoilParMois(ChargeGasoilRequest chargeGasoilRequest) {
        final Long societeId = chargeGasoilRequest.getSocieteId();
        final String matricule = chargeGasoilRequest.getMatricule();
        final LocalDate dateDebut = chargeGasoilRequest.getDateDebut();
        final LocalDate dateFin = chargeGasoilRequest.getDateFin();

        boolean withSocieteId = societeId != null;
        boolean withTransporteurId = matricule != null;
        boolean withDateDebut = dateDebut != null;
        boolean withDateFin = dateFin != null;
        StringBuilder predicate = new StringBuilder(" Where 1=1 ");
        if (withSocieteId) {
            predicate.append(" And g.societeFacturation.id = :societeId");
        }
        if (withTransporteurId) {
            predicate.append(" And g.transporteur.matricule = :matricule");
        }
        if (withDateDebut) {
            predicate.append(" And g.dateBonGasoil >= :dateDebut");
        }
        if (withDateFin) {
            predicate.append(" And g.dateBonGasoil <= :dateFin");
        }

        StringBuilder query = new StringBuilder("Select new com.logistica.service.dto.ChargeGasoilParMois(year(dateBonGasoil), month(dateBonGasoil), sum(prixTotalGasoil)) From Gasoil g").append(predicate).append(" Group By year(dateBonGasoil), month(dateBonGasoil)");
        Query entityQuery = entityManager.createQuery(query.toString());

        if (withSocieteId) {
            entityQuery.setParameter("societeId", societeId);
        }
        if (withTransporteurId) {
            entityQuery.setParameter("matricule", matricule);
        }
        if (withDateDebut) {
            entityQuery.setParameter("dateDebut", dateDebut);
        }
        if (withDateFin) {
            entityQuery.setParameter("dateFin", dateFin);
        }
        return entityQuery.getResultList();
    }

    @Override
    public List<ChargeGasoilParMatricule> getRepartitionChargeGasoilParMatricule(ChargeGasoilRequest chargeGasoilRequest) {
        final Long societeId = chargeGasoilRequest.getSocieteId();
        final String matricule = chargeGasoilRequest.getMatricule();
        final LocalDate dateDebut = chargeGasoilRequest.getDateDebut();
        final LocalDate dateFin = chargeGasoilRequest.getDateFin();

        boolean withSocieteId = societeId != null;
        boolean withTransporteurId = matricule != null;
        boolean withDateDebut = dateDebut != null;
        boolean withDateFin = dateFin != null;
        StringBuilder predicate = new StringBuilder(" Where 1=1 ");
        if (withSocieteId) {
            predicate.append(" And g.societeFacturation.id = :societeId");
        }
        if (withTransporteurId) {
            predicate.append(" And g.transporteur.matricule = :matricule");
        }
        if (withDateDebut) {
            predicate.append(" And g.dateBonGasoil >= :dateDebut");
        }
        if (withDateFin) {
            predicate.append(" And g.dateBonGasoil <= :dateFin");
        }

        StringBuilder query = new StringBuilder("Select new com.logistica.service.dto.ChargeGasoilParMatricule(g.transporteur.matricule, sum(prixTotalGasoil)) From Gasoil g").append(predicate).append(" Group By g.transporteur.matricule");
        Query entityQuery = entityManager.createQuery(query.toString());

        if (withSocieteId) {
            entityQuery.setParameter("societeId", societeId);
        }
        if (withTransporteurId) {
            entityQuery.setParameter("matricule", matricule);
        }
        if (withDateDebut) {
            entityQuery.setParameter("dateDebut", dateDebut);
        }
        if (withDateFin) {
            entityQuery.setParameter("dateFin", dateFin);
        }
        return entityQuery.getResultList();
    }
}
