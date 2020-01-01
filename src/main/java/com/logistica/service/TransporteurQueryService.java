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

import com.logistica.domain.Transporteur;
import com.logistica.domain.*; // for static metamodels
import com.logistica.repository.TransporteurRepository;
import com.logistica.service.dto.TransporteurCriteria;

/**
 * Service for executing complex queries for {@link Transporteur} entities in the database.
 * The main input is a {@link TransporteurCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Transporteur} or a {@link Page} of {@link Transporteur} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TransporteurQueryService extends QueryService<Transporteur> {

    private final Logger log = LoggerFactory.getLogger(TransporteurQueryService.class);

    private final TransporteurRepository transporteurRepository;

    public TransporteurQueryService(TransporteurRepository transporteurRepository) {
        this.transporteurRepository = transporteurRepository;
    }

    /**
     * Return a {@link List} of {@link Transporteur} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Transporteur> findByCriteria(TransporteurCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Transporteur> specification = createSpecification(criteria);
        return transporteurRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Transporteur} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Transporteur> findByCriteria(TransporteurCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Transporteur> specification = createSpecification(criteria);
        return transporteurRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TransporteurCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Transporteur> specification = createSpecification(criteria);
        return transporteurRepository.count(specification);
    }

    /**
     * Function to convert {@link TransporteurCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Transporteur> createSpecification(TransporteurCriteria criteria) {
        Specification<Transporteur> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Transporteur_.id));
            }
            if (criteria.getNom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNom(), Transporteur_.nom));
            }
            if (criteria.getPrenom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPrenom(), Transporteur_.prenom));
            }
            if (criteria.getTelephone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelephone(), Transporteur_.telephone));
            }
            if (criteria.getMatricule() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMatricule(), Transporteur_.matricule));
            }
            if (criteria.getProprietaireId() != null) {
                specification = specification.and(buildSpecification(criteria.getProprietaireId(),
                    root -> root.join(Transporteur_.proprietaire, JoinType.LEFT).get(Societe_.id)));
            }
        }
        return specification;
    }
}
