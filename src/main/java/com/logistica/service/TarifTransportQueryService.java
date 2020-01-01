package com.logistica.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.logistica.domain.TarifTransport;
import com.logistica.domain.*; // for static metamodels
import com.logistica.repository.TarifTransportRepository;
import com.logistica.service.dto.TarifTransportCriteria;

/**
 * Service for executing complex queries for {@link TarifTransport} entities in the database.
 * The main input is a {@link TarifTransportCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TarifTransport} or a {@link Page} of {@link TarifTransport} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TarifTransportQueryService extends QueryService<TarifTransport> {

    private final Logger log = LoggerFactory.getLogger(TarifTransportQueryService.class);

    private final TarifTransportRepository tarifTransportRepository;

    public TarifTransportQueryService(TarifTransportRepository tarifTransportRepository) {
        this.tarifTransportRepository = tarifTransportRepository;
    }

    /**
     * Return a {@link List} of {@link TarifTransport} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TarifTransport> findByCriteria(TarifTransportCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TarifTransport> specification = createSpecification(criteria);
        return tarifTransportRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link TarifTransport} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TarifTransport> findByCriteria(TarifTransportCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TarifTransport> specification = createSpecification(criteria);
        return tarifTransportRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TarifTransportCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TarifTransport> specification = createSpecification(criteria);
        return tarifTransportRepository.count(specification);
    }

    /**
     * Function to convert {@link TarifTransportCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TarifTransport> createSpecification(TarifTransportCriteria criteria) {
        Specification<TarifTransport> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TarifTransport_.id));
            }
            if (criteria.getUnite() != null) {
                specification = specification.and(buildSpecification(criteria.getUnite(), TarifTransport_.unite));
            }
            if (criteria.getPrix() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrix(), TarifTransport_.prix));
            }
            if (criteria.getClientId() != null) {
                specification = specification.and(buildSpecification(criteria.getClientId(),
                    root -> root.join(TarifTransport_.client, JoinType.LEFT).get(Client_.id)));
            }
            if (criteria.getTrajetId() != null) {
                specification = specification.and(buildSpecification(criteria.getTrajetId(),
                    root -> root.join(TarifTransport_.trajet, JoinType.LEFT).get(Trajet_.id)));
            }
            if (criteria.getProduitId() != null) {
                specification = specification.and(buildSpecification(criteria.getProduitId(),
                    root -> root.join(TarifTransport_.produit, JoinType.LEFT).get(Produit_.id)));
            }
        }
        return specification;
    }
}
