package com.logistica.service;

import com.logistica.domain.Trajet;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Trajet}.
 */
public interface TrajetService {

    /**
     * Save a trajet.
     *
     * @param trajet the entity to save.
     * @return the persisted entity.
     */
    Trajet save(Trajet trajet);

    /**
     * Get all the trajets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Trajet> findAll(Pageable pageable);


    /**
     * Get the "id" trajet.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Trajet> findOne(Long id);

    /**
     * Delete the "id" trajet.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
