package com.logistica.service;

import com.logistica.domain.Gasoil;
import com.logistica.domain.Gasoil_;
import com.logistica.domain.Transporteur_;
import com.logistica.repository.GasoilRepository;
import com.logistica.service.dto.GasoilCriteria;
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
 * Service for executing complex queries for {@link Gasoil} entities in the database.
 * The main input is a {@link GasoilCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Gasoil} or a {@link Page} of {@link Gasoil} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class GasoilQueryService extends QueryService<Gasoil> {

    private final Logger log = LoggerFactory.getLogger(GasoilQueryService.class);

    private final GasoilRepository gasoilRepository;

    public GasoilQueryService(GasoilRepository gasoilRepository) {
        this.gasoilRepository = gasoilRepository;
    }

    /**
     * Return a {@link List} of {@link Gasoil} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Gasoil> findByCriteria(GasoilCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Gasoil> specification = createSpecification(criteria);
        return gasoilRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Gasoil} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page     The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Gasoil> findByCriteria(GasoilCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Gasoil> specification = createSpecification(criteria);
        return gasoilRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(GasoilCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Gasoil> specification = createSpecification(criteria);
        return gasoilRepository.count(specification);
    }

    /**
     * Function to convert {@link GasoilCriteria} to a {@link Specification}
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Gasoil> createSpecification(GasoilCriteria criteria) {
        Specification<Gasoil> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Gasoil_.id));
            }
            if (criteria.getSociete() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSociete(), Gasoil_.societe));
            }
            if (criteria.getNumeroBonGasoil() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumeroBonGasoil(), Gasoil_.numeroBonGasoil));
            }
            if (criteria.getMatricule() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMatricule(), Gasoil_.matricule));
            }
            if (criteria.getQuantiteEnLitre() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantiteEnLitre(), Gasoil_.quantiteEnLitre));
            }
            if (criteria.getPrixDuLitre() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrixDuLitre(), Gasoil_.prixDuLitre));
            }
            if (criteria.getPrixTotalGasoil() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrixTotalGasoil(), Gasoil_.prixTotalGasoil));
            }
            if (criteria.getKilometrageInitial() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getKilometrageInitial(), Gasoil_.kilometrageInitial));
            }
            if (criteria.getKilometrageFinal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getKilometrageFinal(), Gasoil_.kilometrageFinal));
            }
            if (criteria.getKilometrageParcouru() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getKilometrageParcouru(), Gasoil_.kilometrageParcouru));
            }
            if (criteria.getTransporteurId() != null) {
                specification = specification.and(buildSpecification(criteria.getTransporteurId(),
                    root -> root.join(Gasoil_.transporteur, JoinType.LEFT).get(Transporteur_.id)));
            }
        }
        return specification;
    }
}
