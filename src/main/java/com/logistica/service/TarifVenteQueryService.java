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

import com.logistica.domain.TarifVente;
import com.logistica.domain.*; // for static metamodels
import com.logistica.repository.TarifVenteRepository;
import com.logistica.service.dto.TarifVenteCriteria;

/**
 * Service for executing complex queries for {@link TarifVente} entities in the database.
 * The main input is a {@link TarifVenteCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TarifVente} or a {@link Page} of {@link TarifVente} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TarifVenteQueryService extends QueryService<TarifVente> {

    private final Logger log = LoggerFactory.getLogger(TarifVenteQueryService.class);

    private final TarifVenteRepository tarifVenteRepository;

    public TarifVenteQueryService(TarifVenteRepository tarifVenteRepository) {
        this.tarifVenteRepository = tarifVenteRepository;
    }

    /**
     * Return a {@link List} of {@link TarifVente} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TarifVente> findByCriteria(TarifVenteCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TarifVente> specification = createSpecification(criteria);
        return tarifVenteRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link TarifVente} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TarifVente> findByCriteria(TarifVenteCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TarifVente> specification = createSpecification(criteria);
        return tarifVenteRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TarifVenteCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TarifVente> specification = createSpecification(criteria);
        return tarifVenteRepository.count(specification);
    }

    /**
     * Function to convert {@link TarifVenteCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TarifVente> createSpecification(TarifVenteCriteria criteria) {
        Specification<TarifVente> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TarifVente_.id));
            }
            if (criteria.getUnite() != null) {
                specification = specification.and(buildSpecification(criteria.getUnite(), TarifVente_.unite));
            }
            if (criteria.getPrix() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrix(), TarifVente_.prix));
            }
            if (criteria.getClientId() != null) {
                specification = specification.and(buildSpecification(criteria.getClientId(),
                    root -> root.join(TarifVente_.client, JoinType.LEFT).get(Client_.id)));
            }
            if (criteria.getProduitId() != null) {
                specification = specification.and(buildSpecification(criteria.getProduitId(),
                    root -> root.join(TarifVente_.produit, JoinType.LEFT).get(Produit_.id)));
            }
        }
        return specification;
    }
}
