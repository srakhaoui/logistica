package com.logistica.service;

import com.logistica.domain.TarifTransport;
import com.logistica.domain.enumeration.Unite;
import com.logistica.repository.TarifTransportRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TarifTransport}.
 */
@Service
@Transactional
public class TarifTransportService {

    private final Logger log = LoggerFactory.getLogger(TarifTransportService.class);

    private final TarifTransportRepository tarifTransportRepository;

    public TarifTransportService(TarifTransportRepository tarifTransportRepository) {
        this.tarifTransportRepository = tarifTransportRepository;
    }

    /**
     * Save a tarifTransport.
     *
     * @param tarifTransport the entity to save.
     * @return the persisted entity.
     */
    public TarifTransport save(TarifTransport tarifTransport) {
        log.debug("Request to save TarifTransport : {}", tarifTransport);
        return tarifTransportRepository.save(tarifTransport);
    }

    /**
     * Get all the tarifTransports.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TarifTransport> findAll(Pageable pageable) {
        log.debug("Request to get all TarifTransports");
        return tarifTransportRepository.findAll(pageable);
    }


    /**
     * Get one tarifTransport by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TarifTransport> findOne(Long id) {
        log.debug("Request to get TarifTransport : {}", id);
        return tarifTransportRepository.findById(id);
    }

    /**
     * Delete the tarifTransport by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TarifTransport : {}", id);
        tarifTransportRepository.deleteById(id);
    }
    
    public Float findPrixByClientProduitUniteAndTrajet(Long clientId, Long produitId, Unite unite, Long trajetId) {
    	return tarifTransportRepository.findPrixByClientProduitUniteAndTrajet(clientId, produitId, unite, trajetId);
    }
}
