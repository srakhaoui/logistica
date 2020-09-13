package com.logistica.service;

import com.logistica.domain.Gasoil;
import com.logistica.service.dto.RecapitulatifChargeGasoil;
import com.logistica.service.dto.RecapitulatifChargeGasoilRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Gasoil}.
 */
public interface GasoilService {

    /**
     * Save a gasoil.
     *
     * @param gasoil the entity to save.
     * @return the persisted entity.
     */
    Gasoil save(Gasoil gasoil);

    /**
     * Get all the gasoils.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Gasoil> findAll(Pageable pageable);


    /**
     * Get the "id" gasoil.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Gasoil> findOne(Long id);

    /**
     * Delete the "id" gasoil.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    Page<RecapitulatifChargeGasoil> getRecapitulatifChargeGasoil(RecapitulatifChargeGasoilRequest recapitulatifChargeGasoilRequest, Pageable pageable);

    Integer getKilometrageFinal(String matricule);
}
