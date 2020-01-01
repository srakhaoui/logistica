package com.logistica.service.impl;

import com.logistica.service.TrajetService;
import com.logistica.domain.Trajet;
import com.logistica.repository.TrajetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Trajet}.
 */
@Service
@Transactional
public class TrajetServiceImpl implements TrajetService {

    private final Logger log = LoggerFactory.getLogger(TrajetServiceImpl.class);

    private final TrajetRepository trajetRepository;

    public TrajetServiceImpl(TrajetRepository trajetRepository) {
        this.trajetRepository = trajetRepository;
    }

    /**
     * Save a trajet.
     *
     * @param trajet the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Trajet save(Trajet trajet) {
        log.debug("Request to save Trajet : {}", trajet);
        return trajetRepository.save(trajet);
    }

    /**
     * Get all the trajets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Trajet> findAll(Pageable pageable) {
        log.debug("Request to get all Trajets");
        return trajetRepository.findAll(pageable);
    }


    /**
     * Get one trajet by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Trajet> findOne(Long id) {
        log.debug("Request to get Trajet : {}", id);
        return trajetRepository.findById(id);
    }

    /**
     * Delete the trajet by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Trajet : {}", id);
        trajetRepository.deleteById(id);
    }
}
