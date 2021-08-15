package com.logistica.service;

import com.logistica.domain.FournisseurGrossiste;
import com.logistica.repository.FournisseurGrossisteRepository;
import com.logistica.service.dto.FournisseurGrossisteCriteria;
import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for executing complex queries for {@link FournisseurGrossiste} entities in the database.
 * The main input is a {@link FournisseurGrossisteCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FournisseurGrossiste} or a {@link Page} of {@link FournisseurGrossiste} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FournisseurGrossisteQueryService extends QueryService<FournisseurGrossiste> {

    private final Logger log = LoggerFactory.getLogger(FournisseurGrossisteQueryService.class);

    private final FournisseurGrossisteRepository fournisseurGrossisteRepository;

    public FournisseurGrossisteQueryService(FournisseurGrossisteRepository fournisseurGrossisteRepository) {
        this.fournisseurGrossisteRepository = fournisseurGrossisteRepository;
    }

    /**
     * Return a {@link List} of {@link FournisseurGrossiste} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FournisseurGrossiste> findByCriteria(FournisseurGrossisteCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FournisseurGrossiste> specification = createSpecification(criteria);
        return fournisseurGrossisteRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link FournisseurGrossiste} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page     The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FournisseurGrossiste> findByCriteria(FournisseurGrossisteCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FournisseurGrossiste> specification = createSpecification(criteria);
        return fournisseurGrossisteRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FournisseurGrossisteCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FournisseurGrossiste> specification = createSpecification(criteria);
        return fournisseurGrossisteRepository.count(specification);
    }

    /**
     * Function to convert {@link FournisseurGrossisteCriteria} to a {@link Specification}
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FournisseurGrossiste> createSpecification(FournisseurGrossisteCriteria criteria) {
        Specification<FournisseurGrossiste> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), FournisseurGrossiste_.id));
            }
            if (criteria.getNom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNom(), FournisseurGrossiste_.nom));
            }
            if (criteria.getAdresse() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAdresse(), FournisseurGrossiste_.adresse));
            }
            if (criteria.getTelephone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelephone(), FournisseurGrossiste_.telephone));
            }
        }
        return specification;
    }
}
