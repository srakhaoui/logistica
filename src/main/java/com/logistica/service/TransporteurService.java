package com.logistica.service;

import com.logistica.domain.Transporteur;
import com.logistica.repository.TransporteurRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Transporteur}.
 */
@Service
@Transactional
public class TransporteurService {

    private final Logger log = LoggerFactory.getLogger(TransporteurService.class);

    private final TransporteurRepository transporteurRepository;

    public TransporteurService(TransporteurRepository transporteurRepository) {
        this.transporteurRepository = transporteurRepository;
    }

    /**
     * Save a transporteur.
     *
     * @param transporteur the entity to save.
     * @return the persisted entity.
     */
    public Transporteur save(Transporteur transporteur) {
        log.debug("Request to save Transporteur : {}", transporteur);
        return transporteurRepository.save(transporteur);
    }

    /**
     * Get all the transporteurs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Transporteur> findAll(Pageable pageable) {
        log.debug("Request to get all Transporteurs");
        return transporteurRepository.findAll(pageable);
    }


    /**
     * Get one transporteur by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Transporteur> findOne(Long id) {
        log.debug("Request to get Transporteur : {}", id);
        return transporteurRepository.findById(id);
    }

    /**
     * Delete the transporteur by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Transporteur : {}", id);
        transporteurRepository.deleteById(id);
    }
}
