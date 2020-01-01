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

import com.logistica.domain.TarifAchat;
import com.logistica.domain.*; // for static metamodels
import com.logistica.repository.TarifAchatRepository;
import com.logistica.service.dto.TarifAchatCriteria;

/**
 * Service for executing complex queries for {@link TarifAchat} entities in the database.
 * The main input is a {@link TarifAchatCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TarifAchat} or a {@link Page} of {@link TarifAchat} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TarifAchatQueryService extends QueryService<TarifAchat> {

    private final Logger log = LoggerFactory.getLogger(TarifAchatQueryService.class);

    private final TarifAchatRepository tarifAchatRepository;

    public TarifAchatQueryService(TarifAchatRepository tarifAchatRepository) {
        this.tarifAchatRepository = tarifAchatRepository;
    }

    /**
     * Return a {@link List} of {@link TarifAchat} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TarifAchat> findByCriteria(TarifAchatCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TarifAchat> specification = createSpecification(criteria);
        return tarifAchatRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link TarifAchat} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TarifAchat> findByCriteria(TarifAchatCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TarifAchat> specification = createSpecification(criteria);
        return tarifAchatRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TarifAchatCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TarifAchat> specification = createSpecification(criteria);
        return tarifAchatRepository.count(specification);
    }

    /**
     * Function to convert {@link TarifAchatCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TarifAchat> createSpecification(TarifAchatCriteria criteria) {
        Specification<TarifAchat> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TarifAchat_.id));
            }
            if (criteria.getUnite() != null) {
                specification = specification.and(buildSpecification(criteria.getUnite(), TarifAchat_.unite));
            }
            if (criteria.getPrix() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrix(), TarifAchat_.prix));
            }
            if (criteria.getFournisseurId() != null) {
                specification = specification.and(buildSpecification(criteria.getFournisseurId(),
                    root -> root.join(TarifAchat_.fournisseur, JoinType.LEFT).get(Fournisseur_.id)));
            }
            if (criteria.getProduitId() != null) {
                specification = specification.and(buildSpecification(criteria.getProduitId(),
                    root -> root.join(TarifAchat_.produit, JoinType.LEFT).get(Produit_.id)));
            }
        }
        return specification;
    }
}
