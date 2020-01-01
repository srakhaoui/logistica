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

import com.logistica.domain.Fournisseur;
import com.logistica.domain.*; // for static metamodels
import com.logistica.repository.FournisseurRepository;
import com.logistica.service.dto.FournisseurCriteria;

/**
 * Service for executing complex queries for {@link Fournisseur} entities in the database.
 * The main input is a {@link FournisseurCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Fournisseur} or a {@link Page} of {@link Fournisseur} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FournisseurQueryService extends QueryService<Fournisseur> {

    private final Logger log = LoggerFactory.getLogger(FournisseurQueryService.class);

    private final FournisseurRepository fournisseurRepository;

    public FournisseurQueryService(FournisseurRepository fournisseurRepository) {
        this.fournisseurRepository = fournisseurRepository;
    }

    /**
     * Return a {@link List} of {@link Fournisseur} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Fournisseur> findByCriteria(FournisseurCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Fournisseur> specification = createSpecification(criteria);
        return fournisseurRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Fournisseur} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Fournisseur> findByCriteria(FournisseurCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Fournisseur> specification = createSpecification(criteria);
        return fournisseurRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FournisseurCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Fournisseur> specification = createSpecification(criteria);
        return fournisseurRepository.count(specification);
    }

    /**
     * Function to convert {@link FournisseurCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Fournisseur> createSpecification(FournisseurCriteria criteria) {
        Specification<Fournisseur> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Fournisseur_.id));
            }
            if (criteria.getNom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNom(), Fournisseur_.nom));
            }
            if (criteria.getAdresse() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAdresse(), Fournisseur_.adresse));
            }
            if (criteria.getTelephone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelephone(), Fournisseur_.telephone));
            }
        }
        return specification;
    }
}
