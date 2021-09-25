package com.logistica.service;

import com.logistica.domain.Gasoil;
import com.logistica.service.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
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

    GasoilPriceResponse getLastPrixGasoil();

    List<ChargeGasoilParMois> getEvolutionChargeGasoilParMois(ChargeGasoilRequest chargeGasoilRequest);

    List<ChargeGasoilParMatricule> getRepartitionChargeGasoilParMatricule(ChargeGasoilRequest chargeGasoilRequest);

    StatistiquesTauxConsommation getStatistiquesTauxConsommation(StatistiquesTauxConsommationRequest tauxConsommationRequest);

    ImportMailiResponse importMaili(MultipartFile mailExport) throws IOException;
}
