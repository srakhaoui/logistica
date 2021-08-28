package com.logistica.service;

import com.logistica.domain.*;
import com.logistica.repository.GasoilAchatGrosRepository;
import com.logistica.service.dto.GasoilAchatGrosCriteria;
import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.JoinType;
import java.util.List;

/**
 * Service for executing complex queries for {@link GasoilAchatGros} entities in the database.
 * The main input is a {@link GasoilAchatGrosCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link GasoilAchatGros} or a {@link Page} of {@link GasoilAchatGros} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class GasoilAchatGrosQueryService extends QueryService<GasoilAchatGros> {

    private final Logger log = LoggerFactory.getLogger(GasoilAchatGrosQueryService.class);

    private final GasoilAchatGrosRepository gasoilAchatGrosRepository;

    public GasoilAchatGrosQueryService(GasoilAchatGrosRepository gasoilAchatGrosRepository) {
        this.gasoilAchatGrosRepository = gasoilAchatGrosRepository;
    }

    /**
     * Return a {@link List} of {@link GasoilAchatGros} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<GasoilAchatGros> findByCriteria(GasoilAchatGrosCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<GasoilAchatGros> specification = createSpecification(criteria);
        return gasoilAchatGrosRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link GasoilAchatGros} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page     The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<GasoilAchatGros> findByCriteria(GasoilAchatGrosCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<GasoilAchatGros> specification = createSpecification(criteria);
        return gasoilAchatGrosRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(GasoilAchatGrosCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<GasoilAchatGros> specification = createSpecification(criteria);
        return gasoilAchatGrosRepository.count(specification);
    }

    /**
     * Function to convert {@link GasoilAchatGrosCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<GasoilAchatGros> createSpecification(GasoilAchatGrosCriteria criteria) {
        Specification<GasoilAchatGros> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), GasoilAchatGros_.id));
            }
            if (criteria.getDateReception() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateReception(), GasoilAchatGros_.dateReception));
            }
            if (criteria.getNumeroBonReception() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNumeroBonReception(), GasoilAchatGros_.numeroBonReception));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), GasoilAchatGros_.description));
            }
            if (criteria.getQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantity(), GasoilAchatGros_.quantity));
            }
            if (criteria.getPrixUnitaire() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrixUnitaire(), GasoilAchatGros_.prixUnitaire));
            }
            if (criteria.getUniteGasoilGros() != null) {
                specification = specification.and(buildSpecification(criteria.getUniteGasoilGros(), GasoilAchatGros_.uniteGasoilGros));
            }
            if (criteria.getFournisseurId() != null) {
                specification = specification.and(buildSpecification(criteria.getFournisseurId(),
                    root -> root.join(GasoilAchatGros_.fournisseurGrossiste, JoinType.LEFT).get(FournisseurGrossiste_.id)));
            }
            if (criteria.getTransporteurId() != null) {
                specification = specification.and(buildSpecification(criteria.getTransporteurId(),
                    root -> root.join(GasoilAchatGros_.acheteur, JoinType.LEFT).get(Societe_.id)));
            }
            if (criteria.getProduitId() != null) {
                specification = specification.and(buildSpecification(criteria.getProduitId(),
                    root -> root.join(GasoilAchatGros_.carburant, JoinType.LEFT).get(Carburant_.id)));
            }
        }
        return specification;
    }
}
