package com.logistica.service;

import com.logistica.domain.Carburant;
import com.logistica.repository.CarburantRepository;
import com.logistica.service.dto.CarburantCriteria;
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
 * Service for executing complex queries for {@link Carburant} entities in the database.
 * The main input is a {@link CarburantCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Carburant} or a {@link Page} of {@link Carburant} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CarburantQueryService extends QueryService<Carburant> {

    private final Logger log = LoggerFactory.getLogger(CarburantQueryService.class);

    private final CarburantRepository carburantRepository;

    public CarburantQueryService(CarburantRepository carburantRepository) {
        this.carburantRepository = carburantRepository;
    }

    /**
     * Return a {@link List} of {@link Carburant} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Carburant> findByCriteria(CarburantCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Carburant> specification = createSpecification(criteria);
        return carburantRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Carburant} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page     The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Carburant> findByCriteria(CarburantCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Carburant> specification = createSpecification(criteria);
        return carburantRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CarburantCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Carburant> specification = createSpecification(criteria);
        return carburantRepository.count(specification);
    }

    /**
     * Function to convert {@link CarburantCriteria} to a {@link Specification}
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Carburant> createSpecification(CarburantCriteria criteria) {
        Specification<Carburant> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Carburant_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), Carburant_.code));
            }
            if (criteria.getCategorie() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCategorie(), Carburant_.categorie));
            }
        }
        return specification;
    }
}
