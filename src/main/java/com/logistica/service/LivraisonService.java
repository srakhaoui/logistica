package com.logistica.service;

import com.logistica.domain.Livraison;
import com.logistica.domain.RecapitulatifAchat;
import com.logistica.service.dto.RecapitulatifAchatRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Livraison}.
 */
public interface LivraisonService {

    /**
     * Save a livraison.
     *
     * @param livraison the entity to save.
     * @return the persisted entity.
     */
    Livraison save(Livraison livraison);

    /**
     * Get all the livraisons.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Livraison> findAll(Pageable pageable);


    /**
     * Get the "id" livraison.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Livraison> findOne(Long id);

    /**
     * Delete the "id" livraison.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
    
    Page<RecapitulatifAchat> getRecapitulatifAchat(RecapitulatifAchatRequest recapitulatifAchatRequest, Pageable pageable);
}
