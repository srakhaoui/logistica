package com.logistica.service;

import com.logistica.domain.GasoilTransfert;
import com.logistica.repository.GasoilTransfertRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Service Implementation for managing {@link GasoilTransfert}.
 */
@Service
@Transactional
public class GasoilTransfertService {

    private final Logger log = LoggerFactory.getLogger(GasoilTransfertService.class);

    private final GasoilTransfertRepository gasoilTransfertRepository;

    private final DepotService depotService;

    public GasoilTransfertService(GasoilTransfertRepository gasoilTransfertRepository, DepotService depotService) {
        this.gasoilTransfertRepository = gasoilTransfertRepository;
        this.depotService = depotService;
    }

    /**
     * Save a gasoilTransfert.
     *
     * @param gasoilTransfert the entity to save.
     * @return the persisted entity.
     */
    public GasoilTransfert save(GasoilTransfert gasoilTransfert) {
        log.debug("Request to save GasoilTransfert : {}", gasoilTransfert);
        gasoilTransfert.setTransfertDate(LocalDate.now());
        if (gasoilTransfert.getQuantite() > depotService.getStock(gasoilTransfert.getSource())) {
            throw new QuantiteGasoilInsuffisanteException();
        }
        return gasoilTransfertRepository.save(gasoilTransfert);
    }

    /**
     * Get all the gasoilTransferts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<GasoilTransfert> findAll(Pageable pageable) {
        log.debug("Request to get all GasoilTransferts");
        return gasoilTransfertRepository.findAll(pageable);
    }


    /**
     * Get one gasoilTransfert by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GasoilTransfert> findOne(Long id) {
        log.debug("Request to get GasoilTransfert : {}", id);
        return gasoilTransfertRepository.findById(id);
    }

    /**
     * Delete the gasoilTransfert by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete GasoilTransfert : {}", id);
        gasoilTransfertRepository.deleteById(id);
    }
}
