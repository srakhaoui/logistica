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

import com.logistica.domain.Trajet;
import com.logistica.domain.*; // for static metamodels
import com.logistica.repository.TrajetRepository;
import com.logistica.service.dto.TrajetCriteria;

/**
 * Service for executing complex queries for {@link Trajet} entities in the database.
 * The main input is a {@link TrajetCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Trajet} or a {@link Page} of {@link Trajet} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TrajetQueryService extends QueryService<Trajet> {

    private final Logger log = LoggerFactory.getLogger(TrajetQueryService.class);

    private final TrajetRepository trajetRepository;

    public TrajetQueryService(TrajetRepository trajetRepository) {
        this.trajetRepository = trajetRepository;
    }

    /**
     * Return a {@link List} of {@link Trajet} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Trajet> findByCriteria(TrajetCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Trajet> specification = createSpecification(criteria);
        return trajetRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Trajet} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Trajet> findByCriteria(TrajetCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Trajet> specification = createSpecification(criteria);
        return trajetRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TrajetCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Trajet> specification = createSpecification(criteria);
        return trajetRepository.count(specification);
    }

    /**
     * Function to convert {@link TrajetCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Trajet> createSpecification(TrajetCriteria criteria) {
        Specification<Trajet> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Trajet_.id));
            }
            if (criteria.getDepart() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDepart(), Trajet_.depart));
            }
            if (criteria.getDestination() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDestination(), Trajet_.destination));
            }
            if (criteria.getCommission() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCommission(), Trajet_.commission));
            }
        }
        return specification;
    }
}
