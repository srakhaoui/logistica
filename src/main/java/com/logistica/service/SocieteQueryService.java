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

import com.logistica.domain.Societe;
import com.logistica.domain.*; // for static metamodels
import com.logistica.repository.SocieteRepository;
import com.logistica.service.dto.SocieteCriteria;

/**
 * Service for executing complex queries for {@link Societe} entities in the database.
 * The main input is a {@link SocieteCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Societe} or a {@link Page} of {@link Societe} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SocieteQueryService extends QueryService<Societe> {

    private final Logger log = LoggerFactory.getLogger(SocieteQueryService.class);

    private final SocieteRepository societeRepository;

    public SocieteQueryService(SocieteRepository societeRepository) {
        this.societeRepository = societeRepository;
    }

    /**
     * Return a {@link List} of {@link Societe} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Societe> findByCriteria(SocieteCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Societe> specification = createSpecification(criteria);
        return societeRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Societe} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Societe> findByCriteria(SocieteCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Societe> specification = createSpecification(criteria);
        return societeRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SocieteCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Societe> specification = createSpecification(criteria);
        return societeRepository.count(specification);
    }

    /**
     * Function to convert {@link SocieteCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Societe> createSpecification(SocieteCriteria criteria) {
        Specification<Societe> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Societe_.id));
            }
            if (criteria.getNom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNom(), Societe_.nom));
            }
        }
        return specification;
    }
}
