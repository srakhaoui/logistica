package com.logistica.service;

import com.logistica.domain.GasoilVenteGros;
import com.logistica.repository.GasoilVenteGrosRepository;
import com.logistica.service.dto.RecapitulatifGasoilGrosRequest;
import com.logistica.service.dto.RecapitulatifGasoilVenteGros;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link GasoilVenteGros}.
 */
@Service
@Transactional
public class GasoilVenteGrosService {

    private final Logger log = LoggerFactory.getLogger(GasoilVenteGrosService.class);

    private final GasoilVenteGrosRepository gasoilVenteGrosRepository;

    public GasoilVenteGrosService(GasoilVenteGrosRepository gasoilVenteGrosRepository) {
        this.gasoilVenteGrosRepository = gasoilVenteGrosRepository;
    }

    /**
     * Save a gasoilVenteGros.
     *
     * @param gasoilVenteGros the entity to save.
     * @return the persisted entity.
     */
    public GasoilVenteGros save(GasoilVenteGros gasoilVenteGros) {
        log.debug("Request to save GasoilVenteGros : {}", gasoilVenteGros);
        //Check that there is enough quantity to sell
        if (gasoilVenteGros.getQuantite() > quantiteDisponibleFrom(gasoilVenteGros)) {
            throw new QuantiteGasoilInsuffisanteException();
        }
        gasoilVenteGros.setPrixVenteTotal(gasoilVenteGros.getPrixVenteUnitaire() * gasoilVenteGros.getQuantite());
        gasoilVenteGros.setMargeGlobale(gasoilVenteGros.getPrixVenteTotal() - (gasoilVenteGros.getAchatGasoil().getPrixUnitaire() * gasoilVenteGros.getAchatGasoil().getQuantity()));
        gasoilVenteGros.setTauxMarge(100 * gasoilVenteGros.getMargeGlobale() / gasoilVenteGros.getPrixVenteTotal());
        return gasoilVenteGrosRepository.save(gasoilVenteGros);
    }

    private double quantiteDisponibleFrom(GasoilVenteGros gasoilVenteGros) {
        return gasoilVenteGros.getAchatGasoil().getQuantity() - Optional.ofNullable(gasoilVenteGrosRepository.getQuantiteVendueParNumeroBonReception(gasoilVenteGros.getAchatGasoil().getNumeroBonReception())).orElse(0.0F);
    }

    /**
     * Get all the gasoilVenteGros.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<GasoilVenteGros> findAll(Pageable pageable) {
        log.debug("Request to get all GasoilVenteGros");
        return gasoilVenteGrosRepository.findAll(pageable);
    }


    /**
     * Get one gasoilVenteGros by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GasoilVenteGros> findOne(Long id) {
        log.debug("Request to get GasoilVenteGros : {}", id);
        return gasoilVenteGrosRepository.findById(id);
    }

    /**
     * Delete the gasoilVenteGros by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete GasoilVenteGros : {}", id);
        gasoilVenteGrosRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<RecapitulatifGasoilVenteGros> getRecapitulatifGasoilVenteGros(RecapitulatifGasoilGrosRequest recapitulatifGasoilGrosRequest, Pageable pageable) {
        return gasoilVenteGrosRepository.getRecapitulatifGasoilVenteGros(recapitulatifGasoilGrosRequest, pageable);
    }
}
