package com.logistica.service;

import com.logistica.domain.ClientGrossiste;
import com.logistica.domain.ClientGrossiste_;
import com.logistica.repository.ClientGrossisteRepository;
import com.logistica.service.dto.ClientGrossisteCriteria;
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
 * Service for executing complex queries for {@link ClientGrossiste} entities in the database.
 * The main input is a {@link ClientGrossisteCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ClientGrossiste} or a {@link Page} of {@link ClientGrossiste} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ClientGrossisteQueryService extends QueryService<ClientGrossiste> {

    private final Logger log = LoggerFactory.getLogger(ClientGrossisteQueryService.class);

    private final ClientGrossisteRepository clientGrossisteRepository;

    public ClientGrossisteQueryService(ClientGrossisteRepository clientGrossisteRepository) {
        this.clientGrossisteRepository = clientGrossisteRepository;
    }

    /**
     * Return a {@link List} of {@link ClientGrossiste} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ClientGrossiste> findByCriteria(ClientGrossisteCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ClientGrossiste> specification = createSpecification(criteria);
        return clientGrossisteRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ClientGrossiste} which matches the criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page     The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ClientGrossiste> findByCriteria(ClientGrossisteCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ClientGrossiste> specification = createSpecification(criteria);
        return clientGrossisteRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ClientGrossisteCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ClientGrossiste> specification = createSpecification(criteria);
        return clientGrossisteRepository.count(specification);
    }

    /**
     * Function to convert {@link ClientGrossisteCriteria} to a {@link Specification}
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ClientGrossiste> createSpecification(ClientGrossisteCriteria criteria) {
        Specification<ClientGrossiste> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ClientGrossiste_.id));
            }
            if (criteria.getNom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNom(), ClientGrossiste_.nom));
            }
            if (criteria.getAdresse() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAdresse(), ClientGrossiste_.adresse));
            }
            if (criteria.getTelephone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelephone(), ClientGrossiste_.telephone));
            }
        }
        return specification;
    }
}
