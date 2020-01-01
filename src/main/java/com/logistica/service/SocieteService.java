package com.logistica.service;

import com.logistica.domain.Societe;
import com.logistica.repository.SocieteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Societe}.
 */
@Service
@Transactional
public class SocieteService {

    private final Logger log = LoggerFactory.getLogger(SocieteService.class);

    private final SocieteRepository societeRepository;

    public SocieteService(SocieteRepository societeRepository) {
        this.societeRepository = societeRepository;
    }

    /**
     * Save a societe.
     *
     * @param societe the entity to save.
     * @return the persisted entity.
     */
    public Societe save(Societe societe) {
        log.debug("Request to save Societe : {}", societe);
        return societeRepository.save(societe);
    }

    /**
     * Get all the societes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Societe> findAll(Pageable pageable) {
        log.debug("Request to get all Societes");
        return societeRepository.findAll(pageable);
    }


    /**
     * Get one societe by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Societe> findOne(Long id) {
        log.debug("Request to get Societe : {}", id);
        return societeRepository.findById(id);
    }

    /**
     * Delete the societe by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Societe : {}", id);
        societeRepository.deleteById(id);
    }
}
