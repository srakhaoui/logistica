package com.logistica.repository;

import com.logistica.service.dto.RecapitulatifGasoilGros;
import com.logistica.service.dto.RecapitulatifGasoilGrosRequest;
import com.logistica.service.dto.RecapitulatifGasoilTransactionGros;
import com.logistica.service.dto.RecapitulatifGasoilVenteGros;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.time.LocalDate;

public class GasoilVenteGrosRepositoryCustomImpl implements GasoilVenteGrosRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<RecapitulatifGasoilVenteGros> getRecapitulatifGasoilVenteGros(RecapitulatifGasoilGrosRequest recapitulatifGasoilGrosRequest, Pageable pageable) {
        final String numeroBonReception = recapitulatifGasoilGrosRequest.getNumeroBonReception();
        final Long fournisseurId = recapitulatifGasoilGrosRequest.getFournisseurId();
        final Long transporteurId = recapitulatifGasoilGrosRequest.getAcheteurId();
        final Long clientId = recapitulatifGasoilGrosRequest.getClientId();
        final Long acheteurId = recapitulatifGasoilGrosRequest.getAcheteurId();
        final LocalDate dateDebut = recapitulatifGasoilGrosRequest.getDateDebut();
        final LocalDate dateFin = recapitulatifGasoilGrosRequest.getDateFin();

        StringBuilder query = new StringBuilder("Select new com.logistica.service.dto.RecapitulatifGasoilVenteGros(g.achatGasoil.description, g.client.nom, g.dateVente, g.achatGasoil.carburant.code, g.quantite, g.uniteGasoilGros, g.prixVenteUnitaire, g.prixVenteTotal) From GasoilVenteGros g");
        boolean withNumeroBonReception = numeroBonReception != null;
        boolean withFournisseurId = fournisseurId != null;
        boolean withAcheteurId = acheteurId != null;
        boolean withTransporteurId = transporteurId != null;
        boolean withClientId = clientId != null;
        boolean withDateDebut = dateDebut != null;
        boolean withDateFin = dateFin != null;
        StringBuilder predicate = new StringBuilder(" Where 1=1 ");
        if (withNumeroBonReception) {
            predicate.append(" And g.achatGasoil.numeroBonReception = :numeroBonReception");
        }
        if (withFournisseurId) {
            predicate.append(" And g.achatGasoil.fournisseurGrossiste.id = :fournisseurId");
        }
        if (withAcheteurId) {
            predicate.append(" And g.achatGasoil.acheteur.id = :acheteurId");
        }
        if (withTransporteurId) {
            predicate.append(" And g.transporteur.id = :transporteurId");
        }
        if (withClientId) {
            predicate.append(" And g.client.id = :clientId");
        }
        if (withDateDebut) {
            predicate.append(" And g.dateVente >= :dateDebut");
        }
        if (withDateFin) {
            predicate.append(" And g.dateVente <= :dateFin");
        }
        String queryAsStr = query.append(predicate.toString()).toString();
        Query entityQuery = entityManager.createQuery(queryAsStr, RecapitulatifGasoilVenteGros.class);
        if (withNumeroBonReception) {
            entityQuery.setParameter("numeroBonReception", numeroBonReception);
        }
        if (withFournisseurId) {
            entityQuery.setParameter("fournisseurId", fournisseurId);
        }
        if (withAcheteurId) {
            entityQuery.setParameter("acheteurId", acheteurId);
        }
        if (withTransporteurId) {
            entityQuery.setParameter("transporteurId", transporteurId);
        }
        if (withClientId) {
            entityQuery.setParameter("clientId", clientId);
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
            String countQuery = new StringBuilder("Select count(*) From GasoilVenteGros g ").append(predicate).toString();
            return entityManager.createQuery(countQuery).getFirstResult();
        });
    }

    @Override
    public Page<RecapitulatifGasoilTransactionGros> getRecapitulatifGasoilTransactionGros(RecapitulatifGasoilGrosRequest recapitulatifGasoilGrosRequest, Pageable pageable) {
        final String numeroBonReception = recapitulatifGasoilGrosRequest.getNumeroBonReception();
        final Long fournisseurId = recapitulatifGasoilGrosRequest.getFournisseurId();
        final Long transporteurId = recapitulatifGasoilGrosRequest.getAcheteurId();
        final Long clientId = recapitulatifGasoilGrosRequest.getClientId();
        final Long acheteurId = recapitulatifGasoilGrosRequest.getAcheteurId();
        final LocalDate dateDebut = recapitulatifGasoilGrosRequest.getDateDebut();
        final LocalDate dateFin = recapitulatifGasoilGrosRequest.getDateFin();

        StringBuilder query = new StringBuilder("Select new com.logistica.service.dto.RecapitulatifGasoilTransactionGros(g.achatGasoil.description, g.achatGasoil.fournisseurGrossiste.nom, g.achatGasoil.acheteur.nom, g.achatGasoil.carburant.code, g.achatGasoil.dateReception, g.achatGasoil.quantity, g.achatGasoil.uniteGasoilGros, g.achatGasoil.prixUnitaire, g.achatGasoil.prixUnitaire * g.achatGasoil.quantity, g.client.nom, g.transporteur.nom, g.dateVente, g.quantite, g.uniteGasoilGros, g.prixVenteUnitaire, g.prixVenteTotal, g.margeGlobale, g.tauxMarge) From GasoilVenteGros g");
        boolean withNumeroBonReception = numeroBonReception != null;
        boolean withFournisseurId = fournisseurId != null;
        boolean withAcheteurId = acheteurId != null;
        boolean withTransporteurId = transporteurId != null;
        boolean withClientId = clientId != null;
        boolean withDateDebut = dateDebut != null;
        boolean withDateFin = dateFin != null;
        StringBuilder predicate = new StringBuilder(" Where 1=1 ");
        if (withNumeroBonReception) {
            predicate.append(" And g.achatGasoil.numeroBonReception = :numeroBonReception");
        }
        if (withFournisseurId) {
            predicate.append(" And g.achatGasoil.fournisseurGrossiste.id = :fournisseurId");
        }
        if (withAcheteurId) {
            predicate.append(" And g.achatGasoil.acheteur.id = :acheteurId");
        }
        if (withTransporteurId) {
            predicate.append(" And g.transporteur.id = :transporteurId");
        }
        if (withClientId) {
            predicate.append(" And g.client.id = :clientId");
        }
        if (withDateDebut) {
            predicate.append(" And g.dateVente >= :dateDebut");
        }
        if (withDateFin) {
            predicate.append(" And g.dateVente <= :dateFin");
        }
        String queryAsStr = query.append(predicate.toString()).toString();
        Query entityQuery = entityManager.createQuery(queryAsStr, RecapitulatifGasoilTransactionGros.class);
        if (withNumeroBonReception) {
            entityQuery.setParameter("numeroBonReception", numeroBonReception);
        }
        if (withFournisseurId) {
            entityQuery.setParameter("fournisseurId", fournisseurId);
        }
        if (withAcheteurId) {
            entityQuery.setParameter("acheteurId", acheteurId);
        }
        if (withTransporteurId) {
            entityQuery.setParameter("transporteurId", transporteurId);
        }
        if (withClientId) {
            entityQuery.setParameter("clientId", clientId);
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
            String countQuery = new StringBuilder("Select count(*) From GasoilVenteGros g ").append(predicate).toString();
            return entityManager.createQuery(countQuery).getFirstResult();
        });
    }

    @Override
    public RecapitulatifGasoilGros getRecapitulatifGasoilGros(RecapitulatifGasoilGrosRequest recapitulatifGasoilGrosRequest) {
        final String numeroBonReception = recapitulatifGasoilGrosRequest.getNumeroBonReception();
        final Long fournisseurId = recapitulatifGasoilGrosRequest.getFournisseurId();
        final Long transporteurId = recapitulatifGasoilGrosRequest.getAcheteurId();
        final Long clientId = recapitulatifGasoilGrosRequest.getClientId();
        final Long acheteurId = recapitulatifGasoilGrosRequest.getAcheteurId();
        final LocalDate dateDebut = recapitulatifGasoilGrosRequest.getDateDebut();
        final LocalDate dateFin = recapitulatifGasoilGrosRequest.getDateFin();

        StringBuilder query = new StringBuilder("Select new com.logistica.service.dto.RecapitulatifGasoilGros(sum(g.achatGasoil.quantity), sum(g.achatGasoil.prixUnitaire * g.achatGasoil.quantity), sum(g.quantite), sum(g.prixVenteTotal)) From GasoilVenteGros g");
        boolean withNumeroBonReception = numeroBonReception != null;
        boolean withFournisseurId = fournisseurId != null;
        boolean withAcheteurId = acheteurId != null;
        boolean withTransporteurId = transporteurId != null;
        boolean withClientId = clientId != null;
        boolean withDateDebut = dateDebut != null;
        boolean withDateFin = dateFin != null;
        StringBuilder predicate = new StringBuilder(" Where 1=1 ");
        if (withNumeroBonReception) {
            predicate.append(" And g.achatGasoil.numeroBonReception = :numeroBonReception");
        }
        if (withFournisseurId) {
            predicate.append(" And g.achatGasoil.fournisseurGrossiste.id = :fournisseurId");
        }
        if (withAcheteurId) {
            predicate.append(" And g.achatGasoil.acheteur.id = :acheteurId");
        }
        if (withTransporteurId) {
            predicate.append(" And g.transporteur.id = :transporteurId");
        }
        if (withClientId) {
            predicate.append(" And g.client.id = :clientId");
        }
        if (withDateDebut) {
            predicate.append(" And g.dateVente >= :dateDebut");
        }
        if (withDateFin) {
            predicate.append(" And g.dateVente <= :dateFin");
        }
        String queryAsStr = query.append(predicate.toString()).toString();
        TypedQuery<RecapitulatifGasoilGros> entityQuery = entityManager.createQuery(queryAsStr, RecapitulatifGasoilGros.class);
        if (withNumeroBonReception) {
            entityQuery.setParameter("numeroBonReception", numeroBonReception);
        }
        if (withFournisseurId) {
            entityQuery.setParameter("fournisseurId", fournisseurId);
        }
        if (withAcheteurId) {
            entityQuery.setParameter("acheteurId", acheteurId);
        }
        if (withTransporteurId) {
            entityQuery.setParameter("transporteurId", transporteurId);
        }
        if (withClientId) {
            entityQuery.setParameter("clientId", clientId);
        }
        if (withDateDebut) {
            entityQuery.setParameter("dateDebut", dateDebut);
        }
        if (withDateFin) {
            entityQuery.setParameter("dateFin", dateFin);
        }

        return entityQuery.getSingleResult();
    }
}
