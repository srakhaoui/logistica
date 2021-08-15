package com.logistica.service;

import com.logistica.domain.Carburant;
import com.logistica.repository.CarburantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Carburant}.
 */
@Service
@Transactional
public class CarburantService {

    private final Logger log = LoggerFactory.getLogger(CarburantService.class);

    private final CarburantRepository carburantRepository;

    public CarburantService(CarburantRepository carburantRepository) {
        this.carburantRepository = carburantRepository;
    }

    /**
     * Save a carburant.
     *
     * @param carburant the entity to save.
     * @return the persisted entity.
     */
    public Carburant save(Carburant carburant) {
        log.debug("Request to save Carburant : {}", carburant);
        return carburantRepository.save(carburant);
    }

    /**
     * Get all the carburants.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Carburant> findAll(Pageable pageable) {
        log.debug("Request to get all Carburants");
        return carburantRepository.findAll(pageable);
    }


    /**
     * Get one carburant by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Carburant> findOne(Long id) {
        log.debug("Request to get Carburant : {}", id);
        return carburantRepository.findById(id);
    }

    /**
     * Delete the carburant by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Carburant : {}", id);
        carburantRepository.deleteById(id);
    }
}
